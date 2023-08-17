package com.example.postservice.service.impl;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;
import com.example.postservice.dto.response.ShipmentRecordDto;
import com.example.postservice.entity.PostItem;
import com.example.postservice.entity.PostOffice;
import com.example.postservice.entity.ShipmentRecord;
import com.example.postservice.enums.PostItemStatus;
import com.example.postservice.mapper.PostItemMapper;
import com.example.postservice.mapper.ShipmentRecordMapper;
import com.example.postservice.repository.PostItemRepository;
import com.example.postservice.service.PostOfficeService;
import com.example.postservice.service.PostService;
import com.example.postservice.service.ShipmentRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private static final String NSE_EXCEPTION_TEST = "No post item with id = %d found";

    private final PostItemRepository postItemRepository;

    private final ShipmentRecordService shipmentRecordService;
    private final PostOfficeService postOfficeService;

    private final PostItemMapper postItemMapper;
    private final ShipmentRecordMapper shipmentRecordMapper;

    @Override
    @Transactional
    public PostItem registerNewPostItem(NewPostItemDto newPostItemDto) {
        PostItem postItem = postItemMapper.toPostItem(newPostItemDto);
        postItem.setStatus(PostItemStatus.ACCEPTED);
        postItem = postItemRepository.save(postItem);
        PostOffice postOffice = postOfficeService.getPostOfficeByIndex(newPostItemDto.getSenderIndex());

        shipmentRecordService.createRegistrationRecord(postItem, postOffice);
        shipmentRecordService.createAcceptanceRecord(postItem, postOffice);
        return postItem;
    }

    @Override
    @Transactional
    public PostItem receivePostItem(PostItemRequestDto requestDto) {
        PostItem postItem = postItemRepository.findById(requestDto.getPostItemId())
                .orElseThrow(() -> new NoSuchElementException(String.format(NSE_EXCEPTION_TEST, requestDto.getPostItemId())));

        if (!postItem.getStatus().equals(PostItemStatus.IN_TRANSIT)) {
            throw new IllegalArgumentException("Cannot receive post item that is not in status IN_TRANSIT");
        }

        PostOffice postOffice = postOfficeService.getPostOfficeByIndex(requestDto.getPostOfficeIndex());

        if (postOffice.getIndex() == postItem.getReceiverIndex()) {
            postItem.setStatus(PostItemStatus.DELIVERED);
            shipmentRecordService.createItemDeliveredRecord(postItem, postOffice);
        } else {
            postItem.setStatus(PostItemStatus.ACCEPTED);
            shipmentRecordService.createAcceptanceRecord(postItem, postOffice);
        }
        postItemRepository.save(postItem);
        return postItem;
    }

    @Override
    @Transactional
    public PostItem dispatchPostItem(PostItemRequestDto requestDto) {
        PostOffice postOffice = postOfficeService.getPostOfficeByIndex(requestDto.getPostOfficeIndex());
        PostItem postItem = postItemRepository.findById(requestDto.getPostItemId())
                .orElseThrow(() -> new NoSuchElementException(String.format(NSE_EXCEPTION_TEST, requestDto.getPostItemId())));
        ShipmentRecord lastShipmentRecord = shipmentRecordService.getLastShipmentRecord(postItem);

        if (!postItem.getStatus().equals(PostItemStatus.ACCEPTED) ||
                !lastShipmentRecord.getText().contains(postOffice.getName())) {
            throw new IllegalArgumentException("PostItem cannot be sent from postOffice where it was not received");
        }

        postItem.setStatus(PostItemStatus.IN_TRANSIT);
        postItemRepository.save(postItem);

        shipmentRecordService.createDepartureRecord(postItem, postOffice);
        return postItem;
    }

    @Override
    @Transactional
    public PostItemTrackingInfo getPostItemTrackingInfo(long postItemId) {
        PostItem postItem = postItemRepository.findById(postItemId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NSE_EXCEPTION_TEST, postItemId)));
        List<ShipmentRecordDto> records = shipmentRecordMapper
                .toDtoList(shipmentRecordService.getAllShipmentRecordsByPostItemId(postItem));
        return new PostItemTrackingInfo(postItemId, postItem.getStatus(), records);
    }

    @Override
    @Transactional
    public PostItem receivePostItemByAddressee(long postItemId) {
        PostItem postItem = postItemRepository.findById(postItemId)
                .orElseThrow(() -> new NoSuchElementException(String.format(NSE_EXCEPTION_TEST, postItemId)));
        if (!postItem.getStatus().equals(PostItemStatus.DELIVERED)) {
            throw new IllegalArgumentException("The post item is not yet delivered to destination facility, " +
                    "it cannot be received");
        }
        postItem.setStatus(PostItemStatus.RECEIVED);
        postItemRepository.save(postItem);

        shipmentRecordService.createItemReceivedRecord(postItem);
        return postItem;
    }
}

package com.example.postservice.service.impl;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;
import com.example.postservice.dto.response.ShipmentRecordDto;
import com.example.postservice.entity.PostItem;
import com.example.postservice.entity.PostOffice;
import com.example.postservice.enums.PostItemStatus;
import com.example.postservice.mapper.PostItemMapper;
import com.example.postservice.mapper.ShipmentRecordMapper;
import com.example.postservice.repository.PostItemRepository;
import com.example.postservice.repository.PostOfficeRepository;
import com.example.postservice.service.PostService;
import com.example.postservice.service.ShipmentRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostItemRepository postItemRepository;
    private final PostOfficeRepository postOfficeRepository;

    private final ShipmentRecordService shipmentRecordService;

    private final PostItemMapper postItemMapper;
    private final ShipmentRecordMapper shipmentRecordMapper;

    @Override
    @Transactional
    public void registerNewPostItem(NewPostItemDto newPostItemDto) {
        PostItem postItem = postItemMapper.toPostItem(newPostItemDto);
        postItem.setStatus(PostItemStatus.ACCEPTED);
        postItem = postItemRepository.save(postItem);
        PostOffice postOffice = postOfficeRepository.findById(newPostItemDto.getSenderIndex())
                        .orElseThrow();

        shipmentRecordService.createRegistrationRecord(postItem, postOffice);
        shipmentRecordService.createAcceptanceRecord(postItem, postOffice);
    }

    @Override
    public void receivePostItem(PostItemRequestDto requestDto) {
        PostOffice postOffice = postOfficeRepository.findById(requestDto.getPostOfficeIndex())
                .orElseThrow();
        PostItem postItem = postItemRepository.findById(requestDto.getPostItemId())
                .orElseThrow();
        postItem.setStatus(PostItemStatus.ACCEPTED);
        postItemRepository.save(postItem);

        shipmentRecordService.createAcceptanceRecord(postItem, postOffice);
    }

    @Override
    public void dispatchPostItem(PostItemRequestDto requestDto) {
        PostOffice postOffice = postOfficeRepository.findById(requestDto.getPostOfficeIndex())
                .orElseThrow();
        PostItem postItem = postItemRepository.findById(requestDto.getPostItemId())
                .orElseThrow();
        postItem.setStatus(PostItemStatus.IN_TRANSIT);
        postItemRepository.save(postItem);

        shipmentRecordService.createDepartureRecord(postItem, postOffice);
    }

    @Override
    public PostItemTrackingInfo getPostItemTrackingInfo(long postItemId) {
        PostItem postItem = postItemRepository.findById(postItemId)
                .orElseThrow();
        List<ShipmentRecordDto> records = shipmentRecordMapper
                .toDtoList(shipmentRecordService.getAllShipmentRecordsByPostItemId(postItem));
        return new PostItemTrackingInfo(postItemId, postItem.getStatus(), records);
    }
}

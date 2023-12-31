package com.example.postservice.unit.service;

import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.entity.PostItem;
import com.example.postservice.entity.PostOffice;
import com.example.postservice.entity.ShipmentRecord;
import com.example.postservice.enums.PostItemStatus;
import com.example.postservice.enums.PostItemType;
import com.example.postservice.repository.PostItemRepository;
import com.example.postservice.service.PostOfficeService;
import com.example.postservice.service.ShipmentRecordService;
import com.example.postservice.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostService должен ")
class PostServiceImplTest {

    private final static long POST_ITEM_ID = 10;
    private final static PostItemType TYPE = PostItemType.PARCEL;
    private final static int RECEIVER_INDEX = 426054;
    private final static String RECEIVER_ADDRESS = "Apple st, 32";
    private final static String RECEIVER_NAME = "Steve Jobs";

    private final static int INDEX = 234567;
    private final static String OFFICE_NAME = "NY-DEP-3";
    private final static String OFFICE_ADDRESS = "Pine st, 56";

    private PostItem postItem;
    private PostItemRequestDto requestDto;
    private PostOffice postOffice;
    private ShipmentRecord shipmentRecord;

    @InjectMocks
    private PostServiceImpl postService;

    @Spy
    private PostItemRepository postItemRepository;
    @Spy
    private PostOfficeService postOfficeService;
    @Spy
    private ShipmentRecordService shipmentRecordService;

    @BeforeEach
    void setUp() {
        postItem = new PostItem(POST_ITEM_ID, TYPE, RECEIVER_INDEX, RECEIVER_ADDRESS, RECEIVER_NAME, PostItemStatus.IN_TRANSIT);
        requestDto = new PostItemRequestDto(postItem.getId(), INDEX);
        postOffice = new PostOffice(INDEX, OFFICE_NAME, OFFICE_ADDRESS);
        shipmentRecord = new ShipmentRecord(3, postItem,
                "Accepted at temporary facility: " + OFFICE_NAME, LocalDateTime.now());
    }

    @Test
    @DisplayName("установить статус 'ACCEPTED' при получении посылки если индекс отделения != индексу получителя")
    void shouldSetStatusAcceptedWhenIndexNotEqualReceiverIndex() {
       when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));
       when(postOfficeService.getPostOfficeByIndex(INDEX)).thenReturn(postOffice);

       PostItem resultPostItem = postService.receivePostItem(requestDto);
       assertEquals(PostItemStatus.ACCEPTED, resultPostItem.getStatus());
    }

    @Test
    @DisplayName("установить статус 'DELIVERED' при получении посылки если индекс отделения = индексу получителя")
    void shouldSetStatusDeliveredWhenIndexEqualReceiverIndex() {
        postOffice.setIndex(RECEIVER_INDEX);
        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));
        when(postOfficeService.getPostOfficeByIndex(INDEX)).thenReturn(postOffice);

        PostItem resultPostItem = postService.receivePostItem(requestDto);
        assertEquals(PostItemStatus.DELIVERED, resultPostItem.getStatus());
    }

    @Test
    @DisplayName("выбросить исключение при получении посылки, которая не была в статусе 'IN_TRANSIT' (в пути)")
    void shouldThrowExceptionWhenTryToReceivePostItemNotInStatusInTransit() {
        postItem.setStatus(PostItemStatus.ACCEPTED);
        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));

        assertThrows(IllegalArgumentException.class, () -> postService.receivePostItem(requestDto));
    }

    @Test
    @DisplayName("установить статус 'IN_TRANSIT' при отправлении посылки")
    void shouldSetStatusInTransitWhenDispatchPostItem(){
        postItem.setStatus(PostItemStatus.ACCEPTED);
        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));
        when(postOfficeService.getPostOfficeByIndex(INDEX)).thenReturn(postOffice);
        when(shipmentRecordService.getLastShipmentRecord(postItem)).thenReturn(shipmentRecord);

        PostItem resultPostItem = postService.dispatchPostItem(requestDto);
        assertEquals(PostItemStatus.IN_TRANSIT, resultPostItem.getStatus());
    }

    @Test
    @DisplayName("выбросить исключение при попытке отправки посылки не в статусе 'ACCEPTED'")
    void shouldThrowExceptionWhenTryToSendOutIfPostItemNotInStatusAccepted(){
        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));
        when(postOfficeService.getPostOfficeByIndex(INDEX)).thenReturn(postOffice);
        when(shipmentRecordService.getLastShipmentRecord(postItem)).thenReturn(shipmentRecord);

        assertThrows(IllegalArgumentException.class, () -> postService.dispatchPostItem(requestDto));
    }

    @Test
    @DisplayName("выбросить исключение при отправления посылки, которая не была получена этим отделением")
    void shouldThrowExceptionWhenTryToSendOutIfPostItemWasNotAcceptedByThisOffice(){
        postItem.setStatus(PostItemStatus.ACCEPTED);
        shipmentRecord.setText("Accepted at temporary facility: Some other post office");

        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));
        when(postOfficeService.getPostOfficeByIndex(INDEX)).thenReturn(postOffice);
        when(shipmentRecordService.getLastShipmentRecord(postItem)).thenReturn(shipmentRecord);

        assertThrows(RuntimeException.class, () -> postService.dispatchPostItem(requestDto));
    }

    @Test
    @DisplayName("установить статус 'RECEIVED' при получении посылки адресатом")
    void shouldSetStatusReceived() {
        postItem.setStatus(PostItemStatus.DELIVERED);
        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));

        PostItem resultPostItem = postService.receivePostItemByAddressee(POST_ITEM_ID);
        assertEquals(PostItemStatus.RECEIVED, resultPostItem.getStatus());
    }

    @Test
    @DisplayName("выбросить исключение при получении посылки не в статусе 'DELIVERED'")
    void shouldThrowExceptionWhenTryToReceiveByAddresseeAndPostItemNotInStatusDelivered() {
        when(postItemRepository.findById(POST_ITEM_ID)).thenReturn(Optional.ofNullable(postItem));

        assertThrows(IllegalArgumentException.class, () -> postService.receivePostItemByAddressee(POST_ITEM_ID));
    }
}
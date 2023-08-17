package com.example.postservice.service.impl;

import com.example.postservice.entity.PostItem;
import com.example.postservice.entity.PostOffice;
import com.example.postservice.entity.ShipmentRecord;
import com.example.postservice.repository.ShipmentRecordRepository;
import com.example.postservice.service.ShipmentRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ShipmentRecordServiceImpl implements ShipmentRecordService {

    private final ShipmentRecordRepository shipmentRecordRepository;

    @Override
    public void createRegistrationRecord(PostItem postItem, PostOffice postOffice) {
        String text = "Registered at " + postOffice.getName();
        createShipmentRecord(postItem, text);
    }

    public void createAcceptanceRecord(PostItem postItem, PostOffice postOffice) {
        String text = "Accepted at temporary facility: " + postOffice.getName();
        createShipmentRecord(postItem, text);
    }

    @Override
    public void createDepartureRecord(PostItem postItem, PostOffice postOffice) {
        String text = "Departed from temporary facility: " + postOffice.getName();
        createShipmentRecord(postItem, text);
    }

    @Override
    public void createItemDeliveredRecord(PostItem postItem, PostOffice postOffice) {
        String text = "Delivered to the destination: " + postOffice.getName();
        createShipmentRecord(postItem, text);
    }

    @Override
    public void createItemReceivedRecord(PostItem postItem) {
        String text = "Received by the addressee" ;
        createShipmentRecord(postItem, text);
    }

    @Override
    public List<ShipmentRecord> getAllShipmentRecordsByPostItemId(PostItem postItem) {
        return shipmentRecordRepository.findAllByPostItemOrderByTime(postItem);
    }

    @Override
    public ShipmentRecord getLastShipmentRecord(PostItem postItem) {
        return shipmentRecordRepository.findFirstByPostItemOrderByTimeDesc(postItem)
                .orElseThrow(() -> new NoSuchElementException("No records for postItem = " + postItem.getId()));
    }

    private void createShipmentRecord(PostItem postItem, String text) {
        ShipmentRecord shipmentRecord = new ShipmentRecord();
        shipmentRecord.setPostItem(postItem);
        shipmentRecord.setTime(LocalDateTime.now());
        shipmentRecord.setText(text);

        shipmentRecordRepository.save(shipmentRecord);
    }
}

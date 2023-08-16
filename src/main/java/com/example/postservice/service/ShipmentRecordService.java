package com.example.postservice.service;

import com.example.postservice.entity.PostItem;
import com.example.postservice.entity.PostOffice;
import com.example.postservice.entity.ShipmentRecord;

import java.util.List;

public interface ShipmentRecordService {

    void createRegistrationRecord(PostItem postItem, PostOffice postOffice);

    void createAcceptanceRecord(PostItem postItem, PostOffice postOffice);

    void createDepartureRecord(PostItem postItem, PostOffice postOffice);

    void createItemDeliveredRecord(PostItem postItem, PostOffice postOffice);

    void createItemReceivedRecord(PostItem postItem);

    List<ShipmentRecord> getAllShipmentRecordsByPostItemId(PostItem postItem);
}

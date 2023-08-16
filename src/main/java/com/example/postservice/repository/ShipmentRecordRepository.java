package com.example.postservice.repository;

import com.example.postservice.entity.PostItem;
import com.example.postservice.entity.ShipmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRecordRepository extends JpaRepository<ShipmentRecord, Long> {

    List<ShipmentRecord> findAllByPostItemOrderByTime(PostItem postItem);
}

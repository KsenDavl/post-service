package com.example.postservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipmentRecordDto {

    private long id;

    private long postItemId;

    private String text;

    LocalDateTime time;
}

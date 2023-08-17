package com.example.postservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipmentRecordDto {

    private long id;

    private String text;

    LocalDateTime time;
}

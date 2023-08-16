package com.example.postservice.dto.response;

import com.example.postservice.enums.PostItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostItemTrackingInfo {

    private Long postItemId;

    private PostItemStatus status;

    List<ShipmentRecordDto> records;


}

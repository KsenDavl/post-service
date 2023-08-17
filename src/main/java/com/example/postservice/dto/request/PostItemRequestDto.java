package com.example.postservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostItemRequestDto {

    private long postItemId;

    private int postOfficeIndex;
}

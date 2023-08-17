package com.example.postservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostItemRequestDto {

    @Schema(example = "1")
    private long postItemId;

    @Schema(example = "546372", maxLength = 9)
    private int postOfficeIndex;
}

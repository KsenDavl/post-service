package com.example.postservice.dto.request;

import com.example.postservice.enums.PostItemType;
import lombok.Data;

@Data
public class NewPostItemDto {

    private PostItemType type;

    private int receiverIndex;

    private String receiverAddress;

    private String receiverName;

    private int senderIndex;
}

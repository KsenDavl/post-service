package com.example.postservice.dto.request;

import com.example.postservice.enums.PostItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPostItemDto {

    @Schema(name = "Тип почтового отправления", example = "LETTER", enumAsRef = true)
    private PostItemType type;

    @Schema(name = "Индекс получателя", example = "111111", maxLength = 9)
    private int receiverIndex;

    @Schema(name = "Адрес получателя", example = "Kipling st, 43")
    private String receiverAddress;

    @Schema(name = "Имя получателя", example = "Max Smith")
    private String receiverName;

    @Schema(name = "Индекс отправителя", example = "222222", maxLength = 9)
    private int senderIndex;
}

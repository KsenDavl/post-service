package com.example.postservice.mapper;

import com.example.postservice.dto.response.ShipmentRecordDto;
import com.example.postservice.entity.ShipmentRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipmentRecordMapper {

    @Mapping(target = "postItemId", expression = "java(shipmentRecord.getPostItem().getId())")
    ShipmentRecordDto toDto(ShipmentRecord shipmentRecord);

    List<ShipmentRecordDto> toDtoList(List<ShipmentRecord> shipmentRecords);
}

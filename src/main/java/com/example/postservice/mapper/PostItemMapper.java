package com.example.postservice.mapper;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.entity.PostItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostItemMapper {

    PostItem toPostItem(NewPostItemDto newPostItemDto);

}

package com.example.postservice.service;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;
import com.example.postservice.entity.PostItem;

public interface PostService {

    PostItem registerNewPostItem(NewPostItemDto newPostItemDto);

    PostItem receivePostItem(PostItemRequestDto requestDto);

    PostItem dispatchPostItem(PostItemRequestDto requestDto);

    PostItemTrackingInfo getPostItemTrackingInfo(long postItemId);

    PostItem receivePostItemByAddressee(long postItemId);

}

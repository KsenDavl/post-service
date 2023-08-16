package com.example.postservice.service;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;

public interface PostService {

    void registerNewPostItem(NewPostItemDto newPostItemDto);

    void receivePostItem(PostItemRequestDto requestDto);

    void dispatchPostItem(PostItemRequestDto requestDto);

    PostItemTrackingInfo getPostItemTrackingInfo(long postItemId);

}

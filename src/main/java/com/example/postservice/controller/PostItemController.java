package com.example.postservice.controller;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;
import com.example.postservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class PostItemController {

    private final PostService postService;

    @PostMapping("/post-item/new")
    public ResponseEntity<Void> registerNewPostItem(@RequestBody NewPostItemDto newPostItemDto) {
        postService.registerNewPostItem(newPostItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/post-item/in")
    public void receivePostItem(@RequestBody PostItemRequestDto requestDto) {
        postService.receivePostItem(requestDto);
        ResponseEntity.ok().build();
    }

    @PostMapping("post-item/out")
    public void dispatchPostItem(@RequestBody PostItemRequestDto requestDto) {
        postService.dispatchPostItem(requestDto);
        ResponseEntity.ok().build();
    }

    @GetMapping("/post-item/tracking/{postItemId}")
    public ResponseEntity<PostItemTrackingInfo> getPostItemTrackingInfo(@PathVariable long postItemId) {
        return ResponseEntity.ok().body(postService.getPostItemTrackingInfo(postItemId));
    }

    @PostMapping("post-item/receiving/{postItemId}")
    public ResponseEntity<Void> receivePostItemByAddressee(@PathVariable long postItemId) {
        postService.receivePostItemByAddressee(postItemId);
        return ResponseEntity.ok().build();
    }
}

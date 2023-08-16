package com.example.postservice.controller;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;
import com.example.postservice.service.PostService;
import lombok.AllArgsConstructor;
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
public class PostController {

    private final PostService postService;

    @PostMapping("/post/new")
    public ResponseEntity<Void> registerNewPostItem(@RequestBody NewPostItemDto newPostItemDto) {
        postService.registerNewPostItem(newPostItemDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/post/in")
    public void receivePostItem(@RequestBody PostItemRequestDto requestDto) {
        postService.receivePostItem(requestDto);
        ResponseEntity.ok().build();
    }

    @PostMapping("post/out")
    public void dispatchPostItem(@RequestBody PostItemRequestDto requestDto) {
        postService.dispatchPostItem(requestDto);
        ResponseEntity.ok().build();
    }

    @GetMapping("/post/tracking/{postItemId}")
    public ResponseEntity<PostItemTrackingInfo> getPostItemTrackingInfo(@PathVariable long postItemId) {
        return ResponseEntity.ok().body(postService.getPostItemTrackingInfo(postItemId));
    }

    @PostMapping("post/receiving/{postItemId}")
    public ResponseEntity<Void> receivePostItemByAddressee(@PathVariable long postItemId) {
        postService.receivePostItemByAddressee(postItemId);
        return ResponseEntity.ok().build();
    }
}

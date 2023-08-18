package com.example.postservice.controller;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.dto.response.PostItemTrackingInfo;
import com.example.postservice.entity.PostItem;
import com.example.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "post-items", description = "REST API для отслеживания почтовых отправлений (ПО)")
public class PostItemController {

    private final PostService postService;

    @PostMapping("/post-item/new")
    @Operation(summary = "Регистрация нового почтового отправления")
    public ResponseEntity<String> registerNewPostItem(@RequestBody NewPostItemDto newPostItemDto) {
        PostItem postItem = postService.registerNewPostItem(newPostItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created post item with id = " + postItem.getId());
    }

    @PostMapping("/post-item/in")
    @Operation(summary = "Прибытие ПО в почтовое отделение")
    public void receivePostItem(@RequestBody PostItemRequestDto requestDto) {
        postService.receivePostItem(requestDto);
        ResponseEntity.ok().build();
    }

    @PostMapping("post-item/out")
    @Operation(summary = "Убытие ПО из почтового отделения")
    public void dispatchPostItem(@RequestBody PostItemRequestDto requestDto) {
        postService.dispatchPostItem(requestDto);
        ResponseEntity.ok().build();
    }

    @GetMapping("/post-item/tracking/{postItemId}")
    @Operation(summary = "Получение статуса ПО и полной истории движения ПО")
    public ResponseEntity<PostItemTrackingInfo> getPostItemTrackingInfo(@PathVariable long postItemId) {
        return ResponseEntity.ok().body(postService.getPostItemTrackingInfo(postItemId));
    }

    @PostMapping("post-item/receiving/{postItemId}")
    @Operation(summary = "Получение ПО адресатом")
    public ResponseEntity<Void> receivePostItemByAddressee(@PathVariable long postItemId) {
        postService.receivePostItemByAddressee(postItemId);
        return ResponseEntity.ok().build();
    }
}

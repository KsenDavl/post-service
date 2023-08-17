package com.example.postservice.controller;

import com.example.postservice.entity.PostOffice;
import com.example.postservice.service.PostOfficeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class PostOfficeController {

    private final PostOfficeService postOfficeService;

    @GetMapping("post-offices")
    public ResponseEntity<List<PostOffice>> getAllPostOffices() {
        return ResponseEntity.ok().body(postOfficeService.getAllPostOffices());
    }
}

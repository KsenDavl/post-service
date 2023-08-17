package com.example.postservice.service.impl;

import com.example.postservice.entity.PostOffice;
import com.example.postservice.repository.PostOfficeRepository;
import com.example.postservice.service.PostOfficeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    @Override
    public PostOffice getPostOfficeByIndex(int index) {
        return postOfficeRepository.findById(index).orElseThrow();
    }

    @Override
    public List<PostOffice> getAllPostOffices() {
        return postOfficeRepository.findAll();
    }
}

package com.example.postservice.service;

import com.example.postservice.entity.PostOffice;

import java.util.List;

public interface PostOfficeService {

    PostOffice getPostOfficeByIndex(int index);

    List<PostOffice> getAllPostOffices();
}

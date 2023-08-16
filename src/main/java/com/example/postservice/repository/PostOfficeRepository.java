package com.example.postservice.repository;

import com.example.postservice.entity.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Integer>  {
}

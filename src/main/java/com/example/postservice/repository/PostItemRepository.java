package com.example.postservice.repository;

import com.example.postservice.entity.PostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItemRepository extends JpaRepository<PostItem, Long> {
}

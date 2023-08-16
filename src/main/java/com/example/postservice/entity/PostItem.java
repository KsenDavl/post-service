package com.example.postservice.entity;

import com.example.postservice.enums.PostItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_items")
public class PostItem {

    @Id
    private long id;

    @Enumerated
    @Column(name = "type")
    private PostItemType type;

    @Column(name = "receiver_index")
    private int receiverIndex;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_name")
    private String receiverName;

}

package com.example.postservice.entity;

import com.example.postservice.enums.PostItemStatus;
import com.example.postservice.enums.PostItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "post_items")
@NoArgsConstructor
@AllArgsConstructor
public class PostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_item_generator")
    @SequenceGenerator(name = "post_item_generator", sequenceName = "post_items_seq", allocationSize = 1)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PostItemType type;

    @Column(name = "receiver_index")
    private int receiverIndex;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_name")
    private String receiverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostItemStatus status;

}

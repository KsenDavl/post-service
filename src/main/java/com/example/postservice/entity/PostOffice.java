package com.example.postservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "post_offices")
@NoArgsConstructor
@AllArgsConstructor
public class PostOffice {

    @Id
    @Column(name = "post_index")
    private int index;

    @Column(name = "post_name")
    private String name;

    @Column(name = "address")
    private String address;
}

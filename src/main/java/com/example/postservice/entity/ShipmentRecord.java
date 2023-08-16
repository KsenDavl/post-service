package com.example.postservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shipment_records")
public class ShipmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_records_generator")
    @SequenceGenerator(name = "shipment_records_generator", sequenceName = "shipment_records_seq", allocationSize = 1)
    private long id;

    @JoinColumn(name = "post_item")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private PostItem postItem;

    @Column(name = "record_text")
    private String text;

    @Column(name = "record_time")
    private LocalDateTime time;
}

package com.example.ramzon.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @JsonIgnore
    @ManyToOne
    private Product product;

    private int quantity;

    private String size;

    private Integer price;

    private Integer discountedPrice;

    private Long userId;

    private LocalDateTime deliverDate;

}

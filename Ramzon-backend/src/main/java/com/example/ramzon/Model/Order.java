package com.example.ramzon.Model;

import com.example.ramzon.RamEnums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String order_id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order",cascade =CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderedDate;

    private LocalDateTime deliveryDate;

    @OneToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetail paymentDetail = new PaymentDetail();

    private double totalPrice;

    private Integer discounte;

    private Integer totalDiscount;

    private OrderStatus orderStatus;

    private int totalItem;

    private  LocalDateTime createdAt;

    private LocalDateTime orderDate;



}

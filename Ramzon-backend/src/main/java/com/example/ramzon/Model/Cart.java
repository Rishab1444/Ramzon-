package com.example.ramzon.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Cart {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToMany
        private Set<CartItem> cartItemSet = new HashSet<>();

        @OneToOne
        @JoinColumn(name = "user_id",nullable = false)
        private User user;


        private double totalPrice;
        
        private int totalItem;
        private int totalDiscountPrice;
        private int discounte;

}

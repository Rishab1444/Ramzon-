package com.example.ramzon.Request;


import lombok.Data;

@Data
public class AddItemReq {

    private Long product_id;
    private String size;
    private int quantity;
    private Integer price;

}

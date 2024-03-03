package com.example.ramzon.Service;

import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.OrderItem;

import java.util.List;

public interface OrderItemService {

    public List<OrderItem> createOrderItem(Cart cart, List<OrderItem> orders);
}

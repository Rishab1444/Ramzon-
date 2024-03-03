package com.example.ramzon.Service;

import com.example.ramzon.Exception.OrderException;
import com.example.ramzon.Model.Address;
import com.example.ramzon.Model.Order;
import com.example.ramzon.Model.User;

import java.util.List;

public interface OrderService {

    public  Order createOrder(User user, Address shippingAddress);

    public  Order findOrderById(Long orderId)throws OrderException;

    public List<Order> userOrderHistory(Long userId);

    public Order placedOrder(Long orderId)throws OrderException;

    public Order confirmedOrder(Long orderId)throws OrderException;

    public Order shippedOrder(Long orderId)throws OrderException;
    public Order deliveredOrder(Long orderId)throws OrderException;
    public Order canceledOrder(Long orderId)throws OrderException;

    public List<Order>getAllOrder();

    public void deleteOrder(Long orderId) throws OrderException;


}

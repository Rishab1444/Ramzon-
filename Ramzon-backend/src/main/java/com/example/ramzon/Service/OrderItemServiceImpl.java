package com.example.ramzon.Service;

import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.CartItem;
import com.example.ramzon.Model.OrderItem;
import com.example.ramzon.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements  OrderItemService{

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> createOrderItem(Cart cart, List<OrderItem> orders) {

        for (CartItem cartItem : cart.getCartItemSet()){
                OrderItem orderItem = new OrderItem();
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setSize(cartItem.getSize());
                orderItem.setUserId(cartItem.getUserId());
                orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            OrderItem createdOrderItem=orderItemRepository.save(orderItem);
            orders.add(createdOrderItem);
        }

        return orders;
    }
}

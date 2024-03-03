package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.User;
import com.example.ramzon.Request.AddItemReq;

public interface CartService {

    public Cart createCart(User user);

    public String  addCartItem(Long userId, AddItemReq req) throws ProductException;

    public Cart findUserCart(Long userId);
}

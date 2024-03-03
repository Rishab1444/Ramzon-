package com.example.ramzon.Service;

import com.example.ramzon.Exception.CartItemException;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.CartItem;
import com.example.ramzon.Model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem)throws CartItemException,UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long cartItemId, Long userid)throws CartItemException,UserException;

    public CartItem findCartItemById (Long id)throws CartItemException;
}

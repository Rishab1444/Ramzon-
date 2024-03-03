package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.CartItem;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Model.User;
import com.example.ramzon.Repository.CartRepository;
import com.example.ramzon.Request.AddItemReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    @Override
    public String addCartItem(Long userId, AddItemReq req) throws ProductException {

        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findByProductById(req.getProduct_id());

        CartItem isPresent = cartItemService.isCartItemExist(cart,product,req.getSize(),userId);

        if (isPresent == null){
            CartItem cartItem  = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItemSet().add(createdCartItem);
        }


        return "Item Added to Cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart  = cartRepository.findByUserId(userId);
        int tp =0;
        int tdp = 0;
        int ti = 0;

        for(CartItem cartItem :cart.getCartItemSet()){
            tp = tp + cartItem.getPrice();
            tdp = tdp + cartItem.getDiscountedPrice();
            ti = ti +  cartItem.getQuantity();
        }
        cart.setTotalPrice(tp);
        cart.setTotalDiscountPrice(tdp);
        cart.setDiscounte(tp-tdp);

        return cartRepository.save(cart);
    }
}

package com.example.ramzon.Service;

import com.example.ramzon.Exception.CartItemException;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.CartItem;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Model.User;
import com.example.ramzon.Repository.CartItemRepository;
import com.example.ramzon.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements  CartItemService{

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;


    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem  = cartItemRepository.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findById(userId);

        if (user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
        }

        return  cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem1 = cartItemRepository.isCartItemExist(cart,product,size,userId);
        return cartItem1;
    }

    @Override
    public void removeCartItem(Long cartItemId, Long userid) throws CartItemException, UserException {
        CartItem cartItem1 = findCartItemById(cartItemId);
        User user = userService.findById(cartItem1.getUserId());

        User reqUser = userService.findById(userid);
        if (user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        } else  {
            throw  new UserException("you can't remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long id) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(id);
        if (opt.isPresent()){
            return opt.get();
        }

        throw new CartItemException("Cartitem not found with id");
    }
}

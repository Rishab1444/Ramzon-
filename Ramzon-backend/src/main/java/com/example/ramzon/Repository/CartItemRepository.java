package com.example.ramzon.Repository;

import com.example.ramzon.Model.Cart;
import com.example.ramzon.Model.CartItem;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


    @Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product, @Param("size")String size, @Param("user")Long userId);

}

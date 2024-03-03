package com.example.ramzon.Repository;

import com.example.ramzon.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository <Cart,Long> {

    @Query("Select c from Cart c where c.user.id = :userId")
    public Cart findByUserId(@Param("userId")Long userId);

}

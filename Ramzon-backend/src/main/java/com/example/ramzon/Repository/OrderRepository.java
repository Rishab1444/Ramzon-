package com.example.ramzon.Repository;

import com.example.ramzon.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("Select o from Order o where o.user.id = :userId")
    public List<Order> findUserOrderHistory(@Param("userId") Long userId);
}

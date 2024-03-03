package com.example.ramzon.Repository;

import com.example.ramzon.Model.Rating;
import com.example.ramzon.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {


    @Query("select r from Review r where r.product.id = :productId")
    public List<Review> findByReviewById(@Param("productId") Long productId);
}

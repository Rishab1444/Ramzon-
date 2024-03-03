package com.example.ramzon.Repository;

import com.example.ramzon.Model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("select r from Rating r where r.product.id = :productId")
    public List<Rating> findByRatingId(@Param("productId") Long productId);
}

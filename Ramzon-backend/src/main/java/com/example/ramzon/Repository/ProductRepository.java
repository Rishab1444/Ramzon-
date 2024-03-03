package com.example.ramzon.Repository;

import com.example.ramzon.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR :category = '' OR p.category.name = :category) " +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.discountedPrice BETWEEN :minPrice AND :maxPrice) " +
            "AND (:minDiscount IS NULL OR p.discountPresent >= :minDiscount) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")

    public List<Product> filterProducts(@Param("category")String category,
                                        @Param("minPrice")Integer minPrice,
                                        @Param("maxPrice")Integer maxPrice,
                                        @Param("minDiscount")Integer minDiscount,
                                        @Param("sort")String sort);

    @Query("select p from Product p where LOWER(p.title) Like %:query% OR LOWER(p.description) Like %:query% OR LOWER(p.brand) LIKE %:query% OR LOWER(p.category.name) LIKE %:query%")
    public List<Product> searchProduct(@Param("query") String q);

    @Query("SELECT p From Product p Where LOWER(p.category.name)=:category")
    public List<Product> findByCategory(@Param("category") String category);

}

package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Model.Rating;
import com.example.ramzon.Model.User;
import com.example.ramzon.Repository.RatingRepository;
import com.example.ramzon.Request.RatingReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    ProductService productService;

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public Rating createRating(RatingReq req, User user) throws ProductException {
        Product product = productService.findByProductById(req.getProduct_id());
        Rating rating  = new Rating();
        rating.setProduct(product);
        rating.setRating(req.getRating());
        rating.setUser(user);
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {
        return ratingRepository.findByRatingId(productId);
    }
}

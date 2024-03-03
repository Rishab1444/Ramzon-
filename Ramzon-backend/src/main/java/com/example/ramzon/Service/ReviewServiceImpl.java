package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Model.Rating;
import com.example.ramzon.Model.Review;
import com.example.ramzon.Model.User;
import com.example.ramzon.Repository.ReviewRepository;
import com.example.ramzon.Request.RatingReq;
import com.example.ramzon.Request.ReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{


    @Autowired
    ProductService productService;
    @Autowired
    ReviewRepository reviewRepository;


    @Override
    public Review createReview(ReviewReq req, User user) throws ProductException {
        Product product = productService.findByProductById(req.getProduct_id());
        Review review  = new Review();
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getProductReview(Long productId) {
        return reviewRepository.findByReviewById(productId);
    }
}

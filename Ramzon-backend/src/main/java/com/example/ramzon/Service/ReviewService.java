package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Rating;
import com.example.ramzon.Model.Review;
import com.example.ramzon.Model.User;
import com.example.ramzon.Request.RatingReq;
import com.example.ramzon.Request.ReviewReq;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewReq req, User user) throws ProductException;

    public List<Review> getProductReview(Long productId);

}

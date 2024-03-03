package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Rating;
import com.example.ramzon.Model.User;
import com.example.ramzon.Request.RatingReq;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingReq req, User user) throws ProductException;

    public List<Rating> getProductRating(Long productId);
}

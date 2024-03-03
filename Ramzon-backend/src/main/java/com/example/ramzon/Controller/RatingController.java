package com.example.ramzon.Controller;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.Rating;
import com.example.ramzon.Model.Review;
import com.example.ramzon.Model.User;
import com.example.ramzon.Request.RatingReq;
import com.example.ramzon.Request.ReviewReq;
import com.example.ramzon.Service.RatingService;
import com.example.ramzon.Service.ReviewService;
import com.example.ramzon.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private RatingService ratingService;
    private UserService userService;

    @Autowired
    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Rating> createReviewHandler(@RequestBody RatingReq req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findByJwt(jwt);
        Rating rating = ratingService.createRating(req,user);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductReviewHandler(@PathVariable Long productId){
        List<Rating> rating =ratingService.getProductRating(productId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }
}

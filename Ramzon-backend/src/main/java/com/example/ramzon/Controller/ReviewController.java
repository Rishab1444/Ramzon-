package com.example.ramzon.Controller;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.Review;
import com.example.ramzon.Model.User;
import com.example.ramzon.Request.ReviewReq;
import com.example.ramzon.Service.ReviewService;
import com.example.ramzon.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

        private ReviewService reviewService;
        private UserService userService;
        @Autowired
         public ReviewController(ReviewService reviewService, UserService userService) {
            this.reviewService = reviewService;
            this.userService = userService;
    }

    @PostMapping("/create")
        public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewReq req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
            User user = userService.findByJwt(jwt);
            Review review = reviewService.createReview(req,user);
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        @GetMapping("/product/{productId}")
        public ResponseEntity<List<Review>> getProductReviewHandler(@PathVariable Long productId){
            List<Review>reviews =reviewService.getProductReview(productId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
}

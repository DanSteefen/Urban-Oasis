package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.Review;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.request.CreateReviewRequest;
import com.urban.oasis.urban.oasis_ecommerce.response.ApiResponse;
import com.urban.oasis.urban.oasis_ecommerce.service.ProductService;
import com.urban.oasis.urban.oasis_ecommerce.service.ReviewService;
import com.urban.oasis.urban.oasis_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {

        List<Review> reviews = reviewService.getReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> writeReview(@RequestBody CreateReviewRequest request,
                                              @PathVariable Long productId,
                                              @RequestHeader("Authorization")String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        Product product = productService.findProductById(productId);
        Review review = reviewService.createReview(request, user, product);
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestBody CreateReviewRequest request,
                                               @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        Review review = reviewService.updateReview(reviewId, request.getReviewText(), request.getReviewRating(), user.getId());
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                    @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse response = new ApiResponse();
        response.setMessage("Review deleted successfully");

        return ResponseEntity.ok(response);
    }

}

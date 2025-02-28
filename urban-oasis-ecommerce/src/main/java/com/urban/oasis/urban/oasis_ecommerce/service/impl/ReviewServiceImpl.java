package com.urban.oasis.urban.oasis_ecommerce.service.impl;

import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.Review;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.repository.ReviewRepository;
import com.urban.oasis.urban.oasis_ecommerce.request.CreateReviewRequest;
import com.urban.oasis.urban.oasis_ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    @Override
    public Review createReview(CreateReviewRequest request, User user, Product product) {

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(review.getReviewText());
        review.setRating(review.getRating());
        review.setProductImage(review.getProductImage());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {

        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String textReview, double rating, Long userId) throws Exception {

        Review review = getReviewById(reviewId);
        if (review.getUser().getId().equals(userId)) {
            review.setReviewText(textReview);
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        throw new Exception("You can't update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = getReviewById(reviewId);
        if (review.getUser().getId().equals(userId)) {
            throw new Exception("You can't delete this review");
        }
        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {

        return reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("Review not found"));
    }
}

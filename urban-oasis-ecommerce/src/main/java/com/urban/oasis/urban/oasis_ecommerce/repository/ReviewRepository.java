package com.urban.oasis.urban.oasis_ecommerce.repository;

import com.urban.oasis.urban.oasis_ecommerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(long productId);
}

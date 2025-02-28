package com.urban.oasis.urban.oasis_ecommerce.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateReviewRequest {

    private String reviewText;
    private double reviewRating;
    private List<String> productImage;
}

package com.urban.oasis.urban.oasis_ecommerce.request;

import lombok.Data;

@Data
public class AddItemRequest {

    private Long productId;
    private String size;
    private int qty;
}

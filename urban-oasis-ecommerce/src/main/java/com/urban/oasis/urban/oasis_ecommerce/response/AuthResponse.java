package com.urban.oasis.urban.oasis_ecommerce.response;

import com.urban.oasis.urban.oasis_ecommerce.domain.USER_ROLES;
import lombok.*;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLES role;
}

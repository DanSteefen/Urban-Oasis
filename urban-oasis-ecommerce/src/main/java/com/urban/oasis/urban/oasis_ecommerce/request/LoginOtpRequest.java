package com.urban.oasis.urban.oasis_ecommerce.request;

import com.urban.oasis.urban.oasis_ecommerce.domain.USER_ROLES;
import lombok.*;

@Data
public class LoginOtpRequest {

    private String email;
    private String otp;
    private USER_ROLES role;
}

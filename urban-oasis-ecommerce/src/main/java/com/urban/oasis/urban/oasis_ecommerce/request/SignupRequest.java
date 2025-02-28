package com.urban.oasis.urban.oasis_ecommerce.request;

import lombok.*;

@Data
public class SignupRequest {

    private String email;
    private String fullName;
    private String mobileNumber;
    private String otp;
}

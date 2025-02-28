package com.urban.oasis.urban.oasis_ecommerce.service;


import com.urban.oasis.urban.oasis_ecommerce.domain.USER_ROLES;
import com.urban.oasis.urban.oasis_ecommerce.request.LoginRequest;
import com.urban.oasis.urban.oasis_ecommerce.response.AuthResponse;
import com.urban.oasis.urban.oasis_ecommerce.request.SignupRequest;

public interface AuthService {

    String createUser(SignupRequest req) throws Exception;
    void sentLoginOtp(String email, USER_ROLES role) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}

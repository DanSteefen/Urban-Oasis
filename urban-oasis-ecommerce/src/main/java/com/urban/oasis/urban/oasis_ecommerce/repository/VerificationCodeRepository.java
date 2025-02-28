package com.urban.oasis.urban.oasis_ecommerce.repository;

import com.urban.oasis.urban.oasis_ecommerce.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    VerificationCode findByEmail(String email);
    VerificationCode findByOtp(String otp);
}

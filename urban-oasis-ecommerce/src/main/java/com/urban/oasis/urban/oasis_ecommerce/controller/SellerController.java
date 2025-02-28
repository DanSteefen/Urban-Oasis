package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.config.JwtProvider;
import com.urban.oasis.urban.oasis_ecommerce.domain.AccountStatus;
import com.urban.oasis.urban.oasis_ecommerce.exception.SellerException;
import com.urban.oasis.urban.oasis_ecommerce.model.Seller;
import com.urban.oasis.urban.oasis_ecommerce.model.SellerReport;
import com.urban.oasis.urban.oasis_ecommerce.model.VerificationCode;
import com.urban.oasis.urban.oasis_ecommerce.repository.VerificationCodeRepository;
import com.urban.oasis.urban.oasis_ecommerce.request.LoginRequest;
import com.urban.oasis.urban.oasis_ecommerce.response.AuthResponse;
import com.urban.oasis.urban.oasis_ecommerce.service.AuthService;
import com.urban.oasis.urban.oasis_ecommerce.service.SellerReportService;
import com.urban.oasis.urban.oasis_ecommerce.service.SellerService;
import com.urban.oasis.urban.oasis_ecommerce.service.impl.EmailServiceImpl;
import com.urban.oasis.urban.oasis_ecommerce.utils.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailServiceImpl emailService;
    private final SellerReportService sellerReportService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller (@RequestBody LoginRequest req) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("seller_" + email);
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail (@PathVariable String otp) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("Wrong otp");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {

        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "Urban Oasis Email Verification Code";
        String text = "Welcome to our Urban Oasis, verify your account using this link";
        String frontend_url = "http://localhost:3000/verify-sellers/";
        emailService.sendVerificationOtpMail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {

        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwtToken(@RequestHeader("Authorization") String jwtToken) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwtToken);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwtToken) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwtToken);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false)AccountStatus status) {

        List<Seller> sellers = sellerService.getAllSellers(status);
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwtToken, @RequestBody Seller seller) throws Exception {

        Seller profile = sellerService.getSellerProfile(jwtToken);
        Seller updateSeller = sellerService.updateSeller(profile.getId(), seller);
        return  ResponseEntity.ok(updateSeller);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {

        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}

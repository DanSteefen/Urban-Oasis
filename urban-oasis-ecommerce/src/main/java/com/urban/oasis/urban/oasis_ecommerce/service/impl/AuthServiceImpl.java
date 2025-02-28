package com.urban.oasis.urban.oasis_ecommerce.service.impl;

import com.urban.oasis.urban.oasis_ecommerce.config.JwtProvider;
import com.urban.oasis.urban.oasis_ecommerce.domain.USER_ROLES;
import com.urban.oasis.urban.oasis_ecommerce.model.Cart;
import com.urban.oasis.urban.oasis_ecommerce.model.Seller;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.model.VerificationCode;
import com.urban.oasis.urban.oasis_ecommerce.repository.CartRepository;
import com.urban.oasis.urban.oasis_ecommerce.repository.SellerRepository;
import com.urban.oasis.urban.oasis_ecommerce.repository.UserRepository;
import com.urban.oasis.urban.oasis_ecommerce.repository.VerificationCodeRepository;
import com.urban.oasis.urban.oasis_ecommerce.request.LoginRequest;
import com.urban.oasis.urban.oasis_ecommerce.response.AuthResponse;
import com.urban.oasis.urban.oasis_ecommerce.request.SignupRequest;
import com.urban.oasis.urban.oasis_ecommerce.service.AuthService;
import com.urban.oasis.urban.oasis_ecommerce.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailServiceImpl emailService;
    private final CustomerUserServiceImpl customerUserService;
    private final SellerRepository sellerRepository;

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong otp...");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if (user == null){
            User createUser = new User();
            createUser.setEmail(req.getEmail());
            createUser.setFullName(req.getFullName());
            createUser.setRole(USER_ROLES.ROLE_CUSTOMER);
            createUser.setMobileNumber(req.getMobileNumber());
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));
            user = userRepository.save(createUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLES.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtProvider.generateToken(authentication);
    }

    @Override
    public void sentLoginOtp(String email, USER_ROLES role) throws Exception {

        String SIGNING_PREFIX = "signing_";

        if (email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            if (role.equals(USER_ROLES.ROLE_SELLER)){
                Seller seller = sellerRepository.findByEmail(email);
                if(seller == null){
                    throw new Exception("Seller not found...");
                }
            } else {
                User user = userRepository.findByEmail(email);
                if(user == null){
                    throw new Exception("User doesn't exist with provided this email...");
                }
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if (isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "Urban Oasis login/signup otp";
        String text = "Your login/signup otp is - " + otp;

        emailService.sendVerificationOtpMail(email, otp, subject, text);

    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {
        
        String username = req.getEmail();
        String otp = req.getOtp();
        
        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login is successfully");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null: authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLES.valueOf(roleName));
        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {

        UserDetails userDetails = customerUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if (username.startsWith(SELLER_PREFIX)){
            username = username.substring(SELLER_PREFIX.length());
        }

        if (userDetails == null){
            throw new BadCredentialsException("Invalid username or password..");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong otp..");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

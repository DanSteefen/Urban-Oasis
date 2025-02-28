package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.domain.AccountStatus;
import com.urban.oasis.urban.oasis_ecommerce.exception.SellerException;
import com.urban.oasis.urban.oasis_ecommerce.model.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws SellerException;
    Seller getSellerByEmail(String email) throws Exception;
    Seller updateSeller(Long id, Seller seller) throws Exception;
    Seller verifyEmail(String email, String otp) throws Exception;
    Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception;
    List<Seller> getAllSellers(AccountStatus status);
    void deleteSeller(Long id) throws Exception;
}

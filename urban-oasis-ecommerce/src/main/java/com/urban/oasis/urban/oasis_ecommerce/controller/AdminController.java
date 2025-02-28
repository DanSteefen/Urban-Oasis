package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.domain.AccountStatus;
import com.urban.oasis.urban.oasis_ecommerce.model.Seller;
import com.urban.oasis.urban.oasis_ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final SellerService sellerService;

    @PatchMapping("/seller/{id}/status{status}")
    public ResponseEntity<Seller> updateSellerStatus(@PathVariable Long id,
                                                     @PathVariable AccountStatus status) throws Exception{

        Seller updateSeller = sellerService.updateSellerAccountStatus(id, status);
        return ResponseEntity.ok(updateSeller);
    }
}

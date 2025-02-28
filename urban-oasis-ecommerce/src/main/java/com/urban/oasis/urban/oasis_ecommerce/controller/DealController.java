package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.model.Deal;
import com.urban.oasis.urban.oasis_ecommerce.response.ApiResponse;
import com.urban.oasis.urban.oasis_ecommerce.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/deals")
public class DealController {

    private final DealService dealService;

    @PostMapping()
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) throws Exception {

        Deal createDeals = dealService.createDeal(deal);
        return new ResponseEntity<>(createDeals, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) throws Exception {

        Deal updateDeal = dealService.updateDeal(deal, id);
        return ResponseEntity.ok(updateDeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) throws Exception {

        dealService.deleteDeal(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deal deleted");
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}

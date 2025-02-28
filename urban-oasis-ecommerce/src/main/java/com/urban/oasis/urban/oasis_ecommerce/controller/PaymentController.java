package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.model.*;
import com.urban.oasis.urban.oasis_ecommerce.response.ApiResponse;
import com.urban.oasis.urban.oasis_ecommerce.response.PaymentLinkResponse;
import com.urban.oasis.urban.oasis_ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(@PathVariable String paymentId, @RequestParam String paymentLinkId,
                                                             @RequestHeader("Authorization") String token) throws Exception {


        User user = userService.findUserByJwtToken(token);
        PaymentLinkResponse paymentLinkResponse;
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);

        boolean paymentSuccess = paymentService.ProceedPaymentOrder(
                paymentOrder, paymentId, paymentLinkId
        );

        if (paymentSuccess){
            for (Order order : paymentOrder.getOrders()) {
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarnings(report.getTotalEarnings() + order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);
            }
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Payment successful");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}

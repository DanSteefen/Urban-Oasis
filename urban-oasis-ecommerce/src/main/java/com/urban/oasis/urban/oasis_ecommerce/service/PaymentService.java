package com.urban.oasis.urban.oasis_ecommerce.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.urban.oasis.urban.oasis_ecommerce.model.Order;
import com.urban.oasis.urban.oasis_ecommerce.model.PaymentOrder;
import com.urban.oasis.urban.oasis_ecommerce.model.User;

import java.util.Set;

public interface PaymentService {

    PaymentOrder createPaymentOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;
    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;
    PaymentLink createRazorPaymentLink(User user, Long amount, Long orderId) throws RazorpayException;
    String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}

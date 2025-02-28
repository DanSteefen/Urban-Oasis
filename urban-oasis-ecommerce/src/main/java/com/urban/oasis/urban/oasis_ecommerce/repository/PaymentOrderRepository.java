package com.urban.oasis.urban.oasis_ecommerce.repository;

import com.urban.oasis.urban.oasis_ecommerce.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long>{

    PaymentOrder findByPaymentLinkId(String paymentId);
}

package com.payment.paymentmanagement.Service;

import com.payment.paymentmanagement.Entity.Payment;
import com.payment.paymentmanagement.Model.PaymentRequest;
import com.payment.paymentmanagement.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Process a new payment request.
     */
    public Payment processPayment(PaymentRequest request) {
        // Simulate payment result (random SUCCESS/FAILED)
        String status = new Random().nextDouble() > 0.2 ? "SUCCESS" : "FAILED";

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .paymentMode(request.getPaymentMode())
                .amount(request.getAmount())
                .status(status)
                .build();

        return paymentRepository.save(payment);
    }

    /**
     * Fetch a payment by its ID.
     */
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }
}

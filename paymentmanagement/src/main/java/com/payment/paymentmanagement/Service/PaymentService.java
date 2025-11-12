package com.payment.paymentmanagement.Service;

import com.payment.paymentmanagement.Client.OrderClient;
import com.payment.paymentmanagement.Entity.Payment;
import com.payment.paymentmanagement.Exception.PaymentNotFoundException;
import com.payment.paymentmanagement.Model.PaymentRequest;
import com.payment.paymentmanagement.Model.OrderResponse;
import com.payment.paymentmanagement.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderClient orderClient;

      public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
}

    public Payment processPayment(PaymentRequest request) {
        
        OrderResponse orderResponse = orderClient.getOrderById(request.getOrderId());
        if (orderResponse == null || orderResponse.getTotalAmount() == null) {
            throw new RuntimeException("Unable to fetch order details for ID " + request.getOrderId());
        }

        Double amount = orderResponse.getTotalAmount();
        String orderMode = (orderResponse.getPaymentMode() == null) ? "" : orderResponse.getPaymentMode().trim().toLowerCase();
        String requestMode = (request.getPaymentMode() == null) ? "" : request.getPaymentMode().trim().toLowerCase();

        String status;

        if (!orderMode.equals(requestMode)) {
            status = "FAILED";
            System.out.println("Payment mode mismatch! Order expects: " + orderMode + ", but got: " + requestMode);
        } else {
        
            switch (requestMode) {
                case "upi", "card", "netbanking" -> {
                    double chance = Math.random();
                    if (chance < 0.8) { 
                        status = "SUCCESS";
                    } else {
                        status = "FAILED";
                    }
                }
                case "cash" -> status = "IN_PROGRESS";
                default -> status = "FAILED";
            }
    }



    Payment payment = new Payment
    (
        request.getOrderId(), 
        request.getPaymentMode(), 
        status);
        payment.setAmount(amount);
        Payment savedPayment = paymentRepository.save(payment);

        System.out.println("Payment processed for Order ID: " + request.getOrderId()
                + " | Mode: " + requestMode.toUpperCase()
                + " | Status: " + savedPayment.getStatus());

        return savedPayment;
}

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID " + id + " not found"));
    }

    @Scheduled(fixedRate = 30000) // every 30 seconds
    public void updatePendingCashPayments() {
        List<Payment> pendingPayments = paymentRepository.findAll()
                .stream()
                .filter(p -> "IN_PROGRESS".equalsIgnoreCase(p.getStatus()))
                .toList();

        for (Payment payment : pendingPayments) {
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
            System.out.println("Cash payment updated to SUCCESS for ID: " + payment.getId());
        }
    }
}
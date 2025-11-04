package com.payment.paymentmanagement.Controller;

import com.payment.paymentmanagement.Entity.Payment;
import com.payment.paymentmanagement.Exception.PaymentNotFoundException;
import com.payment.paymentmanagement.Model.ApiResponse;
import com.payment.paymentmanagement.Model.PaymentRequest;
import com.payment.paymentmanagement.Model.PaymentResponse;
import com.payment.paymentmanagement.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> home() {
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Welcome to Payment Service API",
                        "Use POST /payments to create a payment")
        );
    }

    /**
     * POST /payments
     * Processes a new payment request.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@RequestBody @Valid PaymentRequest request) {
        Payment payment = paymentService.processPayment(request);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentMode(),
                payment.getAmount(),
                payment.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Payment processed successfully", response));
    }

    /**
     * GET /payments/{id}
     * Fetch payment details by payment ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentMode(),
                payment.getAmount(),
                payment.getStatus()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payment retrieved successfully", response)
        );
    }
}

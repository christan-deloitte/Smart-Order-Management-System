package com.payment.paymentmanagement.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;             // Payment ID (auto-generated)
    private Long orderId;        // Order reference
    private String paymentMode;  // Type of payment (CARD, UPI, etc.)
    private Double amount;       // Transaction amount
    private String status;       // SUCCESS / FAILED
}

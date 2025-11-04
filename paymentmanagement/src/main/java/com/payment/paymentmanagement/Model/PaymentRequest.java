package com.payment.paymentmanagement.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;        // ID of the order being paid for
    private String paymentMode;  // CARD / UPI / NETBANKING
    private Double amount;       // Payment amount
}

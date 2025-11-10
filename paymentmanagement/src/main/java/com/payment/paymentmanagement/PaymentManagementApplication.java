package com.payment.paymentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentManagementApplication.class, args);
    }
}

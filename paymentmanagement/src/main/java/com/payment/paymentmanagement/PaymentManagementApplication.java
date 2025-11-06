package com.payment.paymentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@EnableAutoConfiguration
@SpringBootApplication
public class PaymentManagementApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(PaymentManagementApplication.class, args);
    }
}

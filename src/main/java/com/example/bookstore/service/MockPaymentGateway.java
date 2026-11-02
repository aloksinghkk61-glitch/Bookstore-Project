package com.example.bookstore.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Demo gateway: always succeeds.
 * To integrate a real provider (e.g. Razorpay or Stripe), create a new @Service
 * implementing PaymentGateway, call the provider's API in charge(), and remove this one.
 */
@Service
public class MockPaymentGateway implements PaymentGateway {
    @Override
    public Result charge(BigDecimal amount, String method) {
        return new Result(true, "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase(), "OK");
    }
}

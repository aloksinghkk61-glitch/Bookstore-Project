package com.example.bookstore.service;

import java.math.BigDecimal;

/** Swap the implementation to plug in Razorpay / Stripe / PayPal. */
public interface PaymentGateway {
    record Result(boolean success, String transactionId, String message) {}
    Result charge(BigDecimal amount, String method);
}

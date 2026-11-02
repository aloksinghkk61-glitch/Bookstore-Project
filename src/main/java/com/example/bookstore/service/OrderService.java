package com.example.bookstore.service;

import com.example.bookstore.model.*;
import com.example.bookstore.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final CartService cart;
    private final BookRepository books;
    private final OrderRepository orders;
    private final PaymentGateway gateway;

    public OrderService(CartService cart, BookRepository books, OrderRepository orders, PaymentGateway gateway) {
        this.cart = cart; this.books = books; this.orders = orders; this.gateway = gateway;
    }

    /** Stock check, order creation and payment happen in one transaction: any failure rolls everything back. */
    @Transactional
    public CustomerOrder placeOrder(AppUser user, String method) {
        var lines = cart.getLines();
        if (lines.isEmpty()) throw new IllegalStateException("Your cart is empty.");

        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        for (var line : lines) {
            Book book = books.findById(line.book().getId()).orElseThrow();
            if (book.getStock() < line.quantity())
                throw new IllegalStateException("Not enough stock for \"" + book.getTitle() + "\".");
            book.setStock(book.getStock() - line.quantity());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBook(book);
            item.setQuantity(line.quantity());
            item.setUnitPrice(book.getPrice());
            order.getItems().add(item);
        }
        order.setTotal(cart.getTotal());

        var result = gateway.charge(order.getTotal(), method);
        if (!result.success()) throw new IllegalStateException("Payment failed: " + result.message());

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotal());
        payment.setMethod(method);
        payment.setStatus("SUCCESS");
        payment.setTransactionId(result.transactionId());
        order.setPayment(payment);
        order.setStatus("PAID");

        orders.save(order);
        cart.clear();
        return order;
    }
}

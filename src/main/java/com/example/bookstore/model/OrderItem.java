package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "order_items")
@Getter @Setter @NoArgsConstructor
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private CustomerOrder order;
    @ManyToOne(optional = false) private Book book;
    private int quantity;
    private BigDecimal unitPrice;
}

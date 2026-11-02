package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity @Table(name = "orders")
@Getter @Setter @NoArgsConstructor
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private AppUser user;
    private LocalDateTime createdAt = LocalDateTime.now();
    private BigDecimal total;
    private String status; // PAID, SHIPPED, DELIVERED
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}

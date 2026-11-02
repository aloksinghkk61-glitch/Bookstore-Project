package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "payments")
@Getter @Setter @NoArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false) private CustomerOrder order;
    private BigDecimal amount;
    private String method;        // CARD, UPI, COD
    private String status;        // SUCCESS, FAILED
    private String transactionId;
    private LocalDateTime paidAt = LocalDateTime.now();
}

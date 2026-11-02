package com.example.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "books")
@Getter @Setter @NoArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String title;
    @NotBlank private String author;
    private String category;
    @NotNull @DecimalMin("0.0") private BigDecimal price;
    @Min(0) private int stock;
    @Column(length = 1000) private String description;
}

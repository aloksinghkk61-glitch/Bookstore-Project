package com.example.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    @Email @NotBlank @Column(unique = true) private String email;
    @NotBlank @Size(min = 6) private String password;
    private String role = "USER";   // USER or ADMIN
    private String phone;
    private String address;
}

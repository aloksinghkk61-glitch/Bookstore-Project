package com.example.bookstore.config;

import com.example.bookstore.model.*;
import com.example.bookstore.repo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository users;
    private final BookRepository books;
    private final PasswordEncoder encoder;
    @Value("${app.admin.email}") String adminEmail;
    @Value("${app.admin.password}") String adminPassword;

    public DataSeeder(UserRepository users, BookRepository books, PasswordEncoder encoder) {
        this.users = users; this.books = books; this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!users.existsByEmail(adminEmail)) {
            AppUser admin = new AppUser();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode(adminPassword));
            admin.setRole("ADMIN");
            users.save(admin);
        }
        if (books.count() == 0) {
            add("Clean Code", "Robert C. Martin", "Programming", "799", 25, "A handbook of agile software craftsmanship.");
            add("Effective Java", "Joshua Bloch", "Programming", "899", 20, "Best practices for the Java platform.");
            add("Wings of Fire", "A.P.J. Abdul Kalam", "Biography", "299", 40, "Autobiography of India's Missile Man.");
            add("The Alchemist", "Paulo Coelho", "Fiction", "350", 30, "A fable about following your dream.");
            add("Atomic Habits", "James Clear", "Self-Help", "499", 50, "Tiny changes, remarkable results.");
        }
    }

    private void add(String t, String a, String c, String p, int s, String d) {
        Book b = new Book();
        b.setTitle(t); b.setAuthor(a); b.setCategory(c);
        b.setPrice(new BigDecimal(p)); b.setStock(s); b.setDescription(d);
        books.save(b);
    }
}

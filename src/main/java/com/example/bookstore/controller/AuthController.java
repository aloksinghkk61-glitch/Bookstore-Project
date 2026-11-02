package com.example.bookstore.controller;

import com.example.bookstore.model.AppUser;
import com.example.bookstore.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AuthController(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }

    @GetMapping("/login") public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm(Model model) { model.addAttribute("user", new AppUser()); return "register"; }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") AppUser user, BindingResult result) {
        if (users.existsByEmail(user.getEmail()))
            result.rejectValue("email", "dup", "Email already registered");
        if (result.hasErrors()) return "register";
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");   // never trust role from the form
        users.save(user);
        return "redirect:/login?registered";
    }
}

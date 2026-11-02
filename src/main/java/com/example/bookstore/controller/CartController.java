package com.example.bookstore.controller;

import com.example.bookstore.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cart;
    public CartController(CartService cart) { this.cart = cart; }

    @GetMapping
    public String view(Model model) {
        model.addAttribute("lines", cart.getLines());
        model.addAttribute("total", cart.getTotal());
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable Long id) { cart.add(id); return "redirect:/cart"; }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id) { cart.remove(id); return "redirect:/cart"; }
}

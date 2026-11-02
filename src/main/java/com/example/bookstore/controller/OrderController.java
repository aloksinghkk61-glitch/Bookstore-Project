package com.example.bookstore.controller;

import com.example.bookstore.model.*;
import com.example.bookstore.repo.*;
import com.example.bookstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class OrderController {
    private final CartService cart;
    private final OrderService orderService;
    private final OrderRepository orders;
    private final UserRepository users;

    public OrderController(CartService cart, OrderService orderService, OrderRepository orders, UserRepository users) {
        this.cart = cart; this.orderService = orderService; this.orders = orders; this.users = users;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("lines", cart.getLines());
        model.addAttribute("total", cart.getTotal());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String pay(@RequestParam String method, Principal principal, RedirectAttributes ra) {
        AppUser user = users.findByEmail(principal.getName()).orElseThrow();
        try {
            CustomerOrder order = orderService.placeOrder(user, method);
            ra.addFlashAttribute("msg", "Payment successful! Order #" + order.getId() + " placed.");
            return "redirect:/orders";
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/orders")
    public String myOrders(Principal principal, Model model) {
        AppUser user = users.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("orders", orders.findByUserOrderByCreatedAtDesc(user));
        return "orders";
    }
}

package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.repo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookRepository books;
    private final OrderRepository orders;
    public AdminController(BookRepository books, OrderRepository orders) { this.books = books; this.orders = orders; }

    @GetMapping("/books")
    public String list(Model model) {
        model.addAttribute("books", books.findAll());
        model.addAttribute("book", new Book());
        return "admin/books";
    }

    @PostMapping("/books")
    public String add(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
        if (result.hasErrors()) { model.addAttribute("books", books.findAll()); return "admin/books"; }
        books.save(book);
        return "redirect:/admin/books";
    }

    @PostMapping("/books/{id}/stock")
    public String restock(@PathVariable Long id, @RequestParam int stock) {
        books.findById(id).ifPresent(b -> { b.setStock(Math.max(0, stock)); books.save(b); });
        return "redirect:/admin/books";
    }

    @GetMapping("/orders")
    public String allOrders(Model model) {
        model.addAttribute("orders", orders.findAllByOrderByCreatedAtDesc());
        return "admin/orders";
    }
}

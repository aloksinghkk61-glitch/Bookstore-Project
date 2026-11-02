package com.example.bookstore.controller;

import com.example.bookstore.repo.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    private final BookRepository books;
    public BookController(BookRepository books) { this.books = books; }

    @GetMapping({"/", "/books"})
    public String list(@RequestParam(required = false) String q, Model model) {
        boolean searching = q != null && !q.isBlank();
        model.addAttribute("books", searching ? books.search(q.trim()) : books.findAll());
        model.addAttribute("q", q);
        return "index";
    }
}

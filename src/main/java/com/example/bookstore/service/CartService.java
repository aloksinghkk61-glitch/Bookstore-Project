package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repo.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;
import java.util.*;

/** One cart per HTTP session. */
@Component @SessionScope
public class CartService {
    public record Line(Book book, int quantity) {
        public BigDecimal getSubtotal() { return book.getPrice().multiply(BigDecimal.valueOf(quantity)); }
        public Book getBook() { return book; }
        public int getQuantity() { return quantity; }
    }

    private final Map<Long, Integer> items = new LinkedHashMap<>();
    private final BookRepository books;

    public CartService(BookRepository books) { this.books = books; }

    public void add(Long bookId) { items.merge(bookId, 1, Integer::sum); }
    public void remove(Long bookId) { items.remove(bookId); }
    public void clear() { items.clear(); }

    public List<Line> getLines() {
        List<Line> lines = new ArrayList<>();
        items.forEach((id, qty) -> books.findById(id).ifPresent(b -> lines.add(new Line(b, qty))));
        return lines;
    }

    public BigDecimal getTotal() {
        return getLines().stream().map(Line::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getCount() { return items.values().stream().mapToInt(Integer::intValue).sum(); }
}

package com.example.bookstore.repo;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
        SELECT b FROM Book b WHERE
        LOWER(b.title) LIKE LOWER(CONCAT('%', :q, '%')) OR
        LOWER(b.author) LIKE LOWER(CONCAT('%', :q, '%')) OR
        LOWER(b.category) LIKE LOWER(CONCAT('%', :q, '%'))
        """)
    List<Book> search(@Param("q") String q);
}

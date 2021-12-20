package com.feliopolis.bookskeeper.repositories;

import com.feliopolis.bookskeeper.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}

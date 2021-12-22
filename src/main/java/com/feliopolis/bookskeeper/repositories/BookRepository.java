package com.feliopolis.bookskeeper.repositories;

import com.feliopolis.bookskeeper.models.Author;
import com.feliopolis.bookskeeper.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Iterable<Author> findByAuthor(String author);

}

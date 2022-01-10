package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Iterable<Book> findByAuthorId(Long id);

}

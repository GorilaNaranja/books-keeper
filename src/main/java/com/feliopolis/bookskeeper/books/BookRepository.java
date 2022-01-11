package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.books.requests.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long id);

    Optional<Book> findOneByAuthorIdAndName(Long authorId, String name);

}

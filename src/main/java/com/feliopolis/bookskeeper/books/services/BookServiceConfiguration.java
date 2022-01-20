package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.BookCache;
import com.feliopolis.bookskeeper.books.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BookServiceConfiguration {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookCache bookCache;

    @Value("${book.service.performance}")
    private boolean bookServicePerformance;

    @Bean
    public BookService genBookService() {
        if (bookServicePerformance) {
            return new BookServiceConstraint(bookRepository, authorRepository);
        }
        return new BookServiceQuery(bookRepository, authorRepository, bookCache);
    }
}

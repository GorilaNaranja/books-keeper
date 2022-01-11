package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getBooks(Long authorId);

    Optional<Book> getBook(Long id);

    Book createBook(CreateBookRequest book) throws InvalidBookDataException;

    Book editBook(Long id, Book book);

    Book deleteBook(Long id) throws InvalidBookDataException;
}

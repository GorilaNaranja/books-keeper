package com.feliopolis.bookskeeper.authors.services;

import com.feliopolis.bookskeeper.authors.requests.Author;
import com.feliopolis.bookskeeper.authors.requests.CreateAuthorRequest;
import com.feliopolis.bookskeeper.authors.requests.EditAuthorRequest;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAuthors();

    Optional<Author> getAuthor(Long id);

    List<Author> getAuthorByFirstName(String firstName);

    Author createAuthor(CreateAuthorRequest book) throws InvalidBookDataException;

    Author editAuthor(Long id, EditAuthorRequest book) throws InvalidBookDataException, InvocationTargetException, IllegalAccessException;

    Author deleteAuthor(Long id) throws InvalidBookDataException;
}

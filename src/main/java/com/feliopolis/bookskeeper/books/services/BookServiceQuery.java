package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.BookCache;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;
import com.feliopolis.bookskeeper.commons.utils.NullBeanUtils;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookServiceQuery implements BookService {

    /*
     * BookServiceQuery Implementation
     * This implementation use queries to throw a
     * descriptive error at expense of the performance
     */

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookCache bookCache;

    @Override
    public List<Book> getBooks(Long authorId) {
        if (authorId == null) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findByAuthorId(authorId);
        }
    }

    @Override
    public Optional<Book> getBook(Long id) {
        return bookCache.findById(id);
        //return bookRepository.findById(id);
    }

    @Override
    public Book createBook(CreateBookRequest createBookRequest) throws InvalidBookDataException {

        if (authorRepository.findById(createBookRequest.getAuthorId()).isEmpty()) {
            throw new InvalidBookDataException(String.format("Author doesn't exist: %d", createBookRequest.getAuthorId()));
        }
        if (bookRepository.findOneByAuthorIdAndName(createBookRequest.getAuthorId(),
                createBookRequest.getName()).isPresent()) {
            throw new InvalidBookDataException("Book already exist");
        }

        var book = new Book(null,
                createBookRequest.getAuthorId(),
                createBookRequest.getName(),
                createBookRequest.getDescription(),
                createBookRequest.getDate(),
                createBookRequest.getImageUrl());

        return bookRepository.saveAndFlush(book);
    }

    @Override
    public Book editBook(Long id, EditBookRequest editBookRequest) throws InvalidBookDataException, InvocationTargetException, IllegalAccessException {

        var possibleBookDB = bookCache.findById(id);

        if (possibleBookDB.isEmpty()) {
            throw new InvalidBookDataException("Book doesn't exist: " + id);
        }
        if (editBookRequest.getAuthorId() != null && authorRepository.findById(editBookRequest.getAuthorId()).isEmpty()) {
            throw new InvalidBookDataException(String.format("Author doesn't exist: %d", editBookRequest.getAuthorId()));
        }

        var dbBook = possibleBookDB.get();

        NullBeanUtils.getInstance().copyProperties(dbBook, editBookRequest);

        bookRepository.save(dbBook);
        bookCache.deleteById(id);

        return dbBook;

    }

    @Override
    public Book deleteBook(Long id) throws InvalidBookDataException {
        var possibleBook = bookCache.findById(id);

        if (possibleBook.isEmpty()) {
            throw new InvalidBookDataException("Book doesn't exist: " + id);
        }

        bookRepository.deleteById(id);
        bookCache.deleteById(id);

        return possibleBook.get();
    }

}

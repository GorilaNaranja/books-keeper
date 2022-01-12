package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookServiceConstraint implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Book> getBooks(Long authorId) {
        if (authorId == null)
            return bookRepository.findAll();
        else
            return bookRepository.findByAuthorId(authorId);
    }

    @Override
    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book createBook(CreateBookRequest createBookRequest) throws InvalidBookDataException {

        try {
            var book = new Book(null,
                    createBookRequest.getAuthorId(),
                    createBookRequest.getName(),
                    createBookRequest.getDescription(),
                    createBookRequest.getDate(),
                    createBookRequest.getImageUrl());
            return bookRepository.saveAndFlush(book);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new InvalidBookDataException(e.getCause());
            }
            throw e;
        }

    }

    @Override
    public Book editBook(Long id, EditBookRequest book) {
        // TODO: 3 queries needed?
        // TODO: require all fields, if not return null
        // TODO: now returns complete author, return only author id
        if (!bookRepository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
        else {
            Book savedBook = bookRepository.getById(id);
            BeanUtils.copyProperties(book, savedBook, "id");
            bookRepository.saveAndFlush(savedBook);
            return savedBook;
        }
    }

    @Override
    public Book deleteBook(Long id) {
        // TODO: 3 queries needed?
        if (bookRepository.findById(id).isPresent()) {
            Book book = bookRepository.getById(id);
            bookRepository.deleteById(id);
            return book;
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }


}

package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;
import com.zaxxer.hikari.util.PropertyElf;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class BookServiceQuery implements BookService {

    /*
     * BookServiceQuery Implementation
     * This implementation use queries to throw a
     * descriptive error at expense of the performance
     */

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

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
        return bookRepository.findById(id);
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
    public Book editBook(Long id, EditBookRequest editBookRequest) throws InvalidBookDataException {
        if (authorRepository.findById(editBookRequest.getAuthorId()).isEmpty()) {
            throw new InvalidBookDataException(String.format("Author doesn't exist: %d", editBookRequest.getAuthorId()));
        }
        if (bookRepository.findById(id).isEmpty()) {
            throw new InvalidBookDataException("Book doesn't exist: " + id);
        }

        //https://stackoverflow.com/questions/17860520/spring-mvc-patch-method-partial-updates
        // Entity entity = repo.get(id);
        // DTO dto = mapper.map(entity, dtoClass);
        // mapper.map(patchValues, dto);
        // Entity updatedEntity = toEntity(dto);
        // save(updatedEntity);
        // return dto;

        // TODO: edit only requested fields
        Book bookDB = bookRepository.getById(id);
        BeanUtils.copyProperties(bookDB, editBookRequest);
        bookRepository.save(bookDB);

        // bean utils will copy non null values from toBePatched to fromDb manager.
        // BeanUtils beanUtils = null;
        // beanUtils.copyProperties(bookDB, editBookRequest);
        // updateManager(fromDb);

        return bookDB;
    }

    @Override
    public Book deleteBook(Long id) throws InvalidBookDataException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new InvalidBookDataException("Book doesn't exist: " + id);
        }
        Book book = bookRepository.getById(id);
        bookRepository.deleteById(id);
        return book;
    }

}

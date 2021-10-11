package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.Book;
import com.feliopolis.bookskeeper.models.User;
import com.feliopolis.bookskeeper.repositories.BookRepository;
import com.feliopolis.bookskeeper.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> list() {
        return bookRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {

        try {
            return new ResponseEntity<Book>(bookRepository.getById(id), HttpStatus.OK);
        } catch (ResponseStatusException exception) {
            // TODO: is catch working?
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
        }

    }

    @PostMapping
    public Book create(@RequestBody final Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        Book savedBook = bookRepository.getById(id);
        BeanUtils.copyProperties(book, savedBook, "id");
        return bookRepository.saveAndFlush(savedBook);
    }

}



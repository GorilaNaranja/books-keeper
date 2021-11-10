package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.Book;
import com.feliopolis.bookskeeper.repositories.BookRepository;
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
            if (bookRepository.findById(id).isPresent())
                return new ResponseEntity<Book>(bookRepository.getById(id), HttpStatus.OK);
            else
                return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);

        } catch (ResponseStatusException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting book");
        }
    }


    @PostMapping
    public Book create(@RequestBody final Book book) {

        System.out.println("Book! " + book.getName());
        System.out.println("Book! " + book.getDescription());
        System.out.println("Book! " + book.getAuthor());
        System.out.println("Book! " + book.getDate());

        return bookRepository.saveAndFlush(book);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        if (bookRepository.findById(id).isPresent()) {
            Book book = bookRepository.getById(id);
            bookRepository.deleteById(id);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        } else
            return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {

        if (!bookRepository.findById(id).isPresent())
            return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
        else {
            Book savedBook = bookRepository.getById(id);
            BeanUtils.copyProperties(book, savedBook, "id");
            bookRepository.saveAndFlush(savedBook);
            return new ResponseEntity(savedBook, HttpStatus.OK);
        }

    }

}



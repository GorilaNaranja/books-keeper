package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.Book;
import com.feliopolis.bookskeeper.repositories.BookRepository;
import com.feliopolis.bookskeeper.repositories.AuthorRepository;
import com.feliopolis.bookskeeper.repositories.UserRepository;
import com.feliopolis.bookskeeper.utils.ErrorMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity list(@RequestParam(value = "author", required = false) Long id) {
        if (id == null)
            return new ResponseEntity<List<Book>>(bookRepository.findAll(), HttpStatus.OK);
        else
            return new ResponseEntity(bookRepository.findByAuthorId(id), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("")
    Iterable<Book> findByQuery(@RequestParam(value = "id", required = false) Long id) {
        return bookRepository.findByAuthorId(id);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        if (bookRepository.findById(id).isPresent())
            return new ResponseEntity<Book>(bookRepository.getById(id), HttpStatus.OK);
        else
            return new ResponseEntity(new ErrorMessage("404", "Book Not Found"), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Book create(@Valid @RequestBody final Book book) {
        if (!authorRepository.findById(book.getAuthor().getId()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Not Found");
        else
            return new ResponseEntity<Book>(bookRepository.saveAndFlush(book), HttpStatus.OK);
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



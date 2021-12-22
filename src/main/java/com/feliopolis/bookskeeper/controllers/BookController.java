package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.Author;
import com.feliopolis.bookskeeper.models.Book;
import com.feliopolis.bookskeeper.models.User;
import com.feliopolis.bookskeeper.repositories.BookRepository;
import com.feliopolis.bookskeeper.repositories.AuthorRepository;
import com.feliopolis.bookskeeper.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> list() {
        return bookRepository.findAll();
    }

    //@GetMapping
    //@RequestMapping
    //Iterable<Author> findByQuery(@RequestParam(value = "author", required = false) String author) {
    //    return bookRepository.findByAuthor(author);
    //}

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        if (bookRepository.findById(id).isPresent())
            return new ResponseEntity<Book>(bookRepository.getById(id), HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
    }

    @PostMapping
    public Book create(@Valid @RequestBody final Book book) {
        if(!authorRepository.findById(book.getAuthor().getId()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Not Found");
        else
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



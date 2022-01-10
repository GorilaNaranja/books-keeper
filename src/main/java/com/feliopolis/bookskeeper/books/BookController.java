package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public final ResponseEntity list(@RequestParam(value = "author", required = false) Long id) {
        if (id == null)
            return new ResponseEntity<List<Book>>(bookRepository.findAll(), HttpStatus.OK);
        else
            return new ResponseEntity(bookRepository.findByAuthorId(id), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        if (bookRepository.findById(id).isPresent())
            return new ResponseEntity<Book>(bookRepository.getById(id), HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody final Book book) {
        if (!authorRepository.findById(book.getAuthor().getId()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Not Found");
        else
            return new ResponseEntity<Book>(bookRepository.saveAndFlush(book), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Book delete(@PathVariable Long id) {
        // TODO aqui no hay logica, llamar a un service
        // Controller solo inyecta servicios
        // Servicio solo inyecta otros servicios o repository
        if (bookRepository.findById(id).isPresent()) {
            Book book = bookRepository.getById(id);
            bookRepository.deleteById(id);
            return book;
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
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



package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import lombok.RequiredArgsConstructor;
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

    /*
    Controller doesn't has logic
    Controller call services
    Services call other services or repositories
    */

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    // TODO: add try catch, exception type?

    @GetMapping
    public ResponseEntity list(@RequestParam(value = "author", required = false) Long authorId) {
        return new ResponseEntity<List<Book>>(bookService.getBooks(authorId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        return new ResponseEntity<Book>(bookService.getBook(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody final Book book) {
        return new ResponseEntity<Book>(bookService.createBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(bookService.deleteBook(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        return new ResponseEntity(bookService.editBook(id, book), HttpStatus.OK);
    }

}



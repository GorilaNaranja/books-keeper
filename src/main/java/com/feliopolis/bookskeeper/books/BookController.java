package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.services.BookService;
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

    private final BookService bookService;

    // TODO: try catch, exception type?

    @GetMapping
    public ResponseEntity list(@RequestParam(value = "author", required = false) Long authorId) {
        try {
            return new ResponseEntity<List<Book>>(bookService.getBooks(authorId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        return bookService
                .getBook(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @PostMapping
    public Book create(@Valid @RequestBody final CreateBookRequest book) {
        try {
            return bookService.createBook(book);
        } catch (InvalidBookDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity  delete(@PathVariable Long id) throws InvalidBookDataException {

//        return bookService
//                .deleteBook(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        try {
            return new ResponseEntity(bookService.deleteBook(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        try {
            return new ResponseEntity(bookService.editBook(id, book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;
import com.feliopolis.bookskeeper.books.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"Books"})
public class BookController {

    /*
    Controller doesn't has logic
    Controller call services
    Services call other services or repositories
    */

    private final BookService bookService;

    // TODO: try catch, why not orElseThrow in delete?

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
    public Book delete(@PathVariable Long id) throws InvalidBookDataException {
        try {
            return bookService.deleteBook(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody EditBookRequest book) {
        try {
            return bookService.editBook(id, book);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}



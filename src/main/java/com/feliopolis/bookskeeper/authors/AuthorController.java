package com.feliopolis.bookskeeper.authors;

import com.feliopolis.bookskeeper.authors.requests.Author;
import com.feliopolis.bookskeeper.authors.requests.CreateAuthorRequest;
import com.feliopolis.bookskeeper.authors.requests.EditAuthorRequest;
import com.feliopolis.bookskeeper.authors.services.AuthorService;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"Authors"})
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity list() {
        try {
            return new ResponseEntity<List<Author>>(authorService.getAuthors(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Author get(@PathVariable Long id) {
        return authorService
                .getAuthor(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    @GetMapping("/search")
    public ResponseEntity findByFirstName(@RequestParam(value = "name", required = true) String firstName) {
        try {
            return new ResponseEntity<List<Author>>(authorService.getAuthorByFirstName(firstName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public Author create(@Valid @RequestBody final CreateAuthorRequest author) {
        try {
            return authorService.createAuthor(author);
        } catch (InvalidBookDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Author delete(@PathVariable Long id) throws InvalidBookDataException {
        try {
            return authorService.deleteAuthor(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public Author update(@PathVariable Long id, @RequestBody EditAuthorRequest author) {
        try {
            return authorService.editAuthor(id, author);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}



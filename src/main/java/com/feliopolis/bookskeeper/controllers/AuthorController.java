package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.Author;
import com.feliopolis.bookskeeper.repositories.AuthorRepository;
import com.feliopolis.bookskeeper.utils.ErrorMessage;
import com.feliopolis.bookskeeper.utils.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> list() {
        return authorRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Author get(@PathVariable Long id) {
        return authorRepository.getById(id);
    }

    @GetMapping
    @RequestMapping("/search")
    Iterable<Author> findByQuery(@RequestParam(value = "name", required = false) String firstName) {
        return authorRepository.findByFirstName(firstName);
    }

//    @PostMapping
//    public Author create(@Valid @RequestBody Author author) {
//        return authorRepository.save(author);
//    }

    @PostMapping
    public Author create(@RequestBody final Author author) throws ValidationException {
        if (author.getFirst_name() != null && author.getLast_name() != null)
            return authorRepository.save(author);
        else throw new ValidationException("Author cannot be created");
    }

//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public Author update(@PathVariable Long id, @RequestBody Author author) {
//        return authorRepository.save(author);
//    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) {
        if (authorRepository.findById(id).isPresent()) {
            return new ResponseEntity(authorRepository.save(author), HttpStatus.OK);
        } else
            return new ResponseEntity(author, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }


//    @GetMapping
//    @RequestMapping("{id}")
//    public ResponseEntity<Author> get(@PathVariable Long id) {
//        try {
//            return new ResponseEntity<Author>(authorRepository.getById(id), HttpStatus.OK);
//        } catch (ResponseStatusException exception) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
//        }
//    }


//    @PostMapping
//    public Author create(@Valid @RequestBody Author author) {
//        return authorRepository.save(author);
//    }
//
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable Long id) {
//        authorRepository.deleteById(id);
//    }


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    List<FieldErrorMessage> exceptionHandle(MethodArgumentNotValidException e) {
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        List<FieldErrorMessage> fieldErrorsMessage = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
//
//        return fieldErrorsMessage;
//    }

}



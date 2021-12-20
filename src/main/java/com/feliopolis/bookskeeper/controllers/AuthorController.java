package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.Author;
import com.feliopolis.bookskeeper.repositories.AuthorRepository;
import com.feliopolis.bookskeeper.utils.FieldErrorMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public Author get(@PathVariable Integer id) {
        return authorRepository.getById(id);
    }

    @GetMapping
    @RequestMapping("/search")
    Iterable<Author> findByQuery(@RequestParam(value = "name", required = false) String firstName) {
        return authorRepository.findByFirstName(firstName);
    }

    @PostMapping
    public Author create(@Valid @RequestBody Author author) {
        return authorRepository.save(author);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        authorRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author) {
        System.out.println(authorRepository.findById(id).isPresent());
        if (authorRepository.findById(id).isPresent()) {
            Author savedAuthor = authorRepository.getById(id);
            BeanUtils.copyProperties(author, savedAuthor, "id");
            authorRepository.saveAndFlush(savedAuthor);
            return new ResponseEntity(savedAuthor, HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found");
    }

    @Validated
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandle(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());

        return fieldErrorMessages;
    }


}



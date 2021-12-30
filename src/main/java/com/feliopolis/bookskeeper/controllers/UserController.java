package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.enums.Roles;
import com.feliopolis.bookskeeper.models.Role;
import com.feliopolis.bookskeeper.models.User;
import com.feliopolis.bookskeeper.repositories.RoleRepository;
import com.feliopolis.bookskeeper.repositories.UserRepository;
import com.feliopolis.bookskeeper.security.JwtUtils;
import com.feliopolis.bookskeeper.services.UserDetailsImpl;
import com.feliopolis.bookskeeper.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        System.out.println("GET BY ID " + id);

        if (userRepository.findById(id).isPresent()) {
            System.out.println("USER WITH ID " + id + " EXIST");
            return new ResponseEntity<>(userRepository.getById(id), HttpStatus.OK);
        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            System.out.println("USER WITH ID " + id + " DOESNT EXIST");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

    }

    @PostMapping
    public User create(@Valid @RequestBody final User user) {
        return userRepository.save(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        if (userRepository.findById(id).isPresent())
            userRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {

        if (userRepository.findById(id).isPresent()) {
            User savedUser = userRepository.getById(id);
            BeanUtils.copyProperties(user, savedUser, "password", "id");
            userRepository.saveAndFlush(savedUser);
            return new ResponseEntity(savedUser, HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
    }

    @Validated
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandle(MethodArgumentNotValidException e) {
        System.out.println("EXCEPTION HANDLER");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());

        return fieldErrorMessages;
    }

}



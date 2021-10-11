package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.models.User;
import com.feliopolis.bookskeeper.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

        return new ResponseEntity<>(userRepository.getById(id), HttpStatus.OK);

        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //try {
        //return new ResponseEntity<User>(userRepository.getById(id), HttpStatus.OK);
        //} catch (ResponseStatusException exception) {
        // TODO: is catch working?
        //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        //}
    }

    @PostMapping
    public User create(@RequestBody final User user) {
        return userRepository.saveAndFlush(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user) {
        User savedUser = userRepository.getById(id);
        BeanUtils.copyProperties(user, savedUser, "password", "id");
        return userRepository.saveAndFlush(savedUser);
    }

}



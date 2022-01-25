package com.feliopolis.bookskeeper.authors;

import com.feliopolis.bookskeeper.authors.requests.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFirstNameContainingAllIgnoreCase(String firstName);

    Optional<Author> findOneByFirstNameAndLastName(String firstName, String lastName);
}

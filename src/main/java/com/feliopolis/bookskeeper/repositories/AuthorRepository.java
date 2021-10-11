package com.feliopolis.bookskeeper.repositories;

import com.feliopolis.bookskeeper.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Iterable<Author> findByFirstName(String firstName);
}

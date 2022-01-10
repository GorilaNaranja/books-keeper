package com.feliopolis.bookskeeper.authors;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Iterable<Author> findByFirstName(String firstName);
}

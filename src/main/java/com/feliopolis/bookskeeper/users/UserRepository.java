package com.feliopolis.bookskeeper.users;

import com.feliopolis.bookskeeper.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String firstName);

    Optional<User> findByEmail(String email);

    Boolean existsByFirstName(String firstName);

    Boolean existsByEmail(String email);
}

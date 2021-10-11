package com.feliopolis.bookskeeper.repositories;

import com.feliopolis.bookskeeper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

package com.feliopolis.bookskeeper.repositories;

import com.feliopolis.bookskeeper.enums.Roles;
import com.feliopolis.bookskeeper.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(Roles role);

}
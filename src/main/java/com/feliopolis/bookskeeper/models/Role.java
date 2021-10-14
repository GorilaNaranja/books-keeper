package com.feliopolis.bookskeeper.models;


import javax.persistence.*;

@Entity
//@Table(name = "ppp_role", uniqueConstraints = {@UniqueConstraint(name = "APP_ROLE_UK", columnNames = "Role_Name")})
public class Role {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role", nullable = false)
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

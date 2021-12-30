package com.feliopolis.bookskeeper.models;


<<<<<<< Updated upstream
import javax.persistence.*;

@Entity
//@Table(name = "ppp_role", uniqueConstraints = {@UniqueConstraint(name = "APP_ROLE_UK", columnNames = "Role_Name")})
public class Role {

    @Id
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "role", nullable = false)
    private String roleName;

    public Long getId() {
        return roleId;
    }

    public void setId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return roleName;
    }

    public void setRole(String roleName) {
        this.roleName = roleName;
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feliopolis.bookskeeper.enums.Roles;

import javax.persistence.*;

@Entity(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
>>>>>>> Stashed changes
    }
}

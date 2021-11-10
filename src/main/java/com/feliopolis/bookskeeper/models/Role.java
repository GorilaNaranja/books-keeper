package com.feliopolis.bookskeeper.models;


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
    }
}

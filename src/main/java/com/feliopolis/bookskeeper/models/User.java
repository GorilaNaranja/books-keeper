package com.feliopolis.bookskeeper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feliopolis.bookskeeper.enums.Roles;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    //@NotNull(message = "First name may not be null")
    //@NotBlank(message = "First name required")
    private String firstName;

    @Column(name = "last_name")
    //@NotNull(message = "Last name may not be null")
    //@NotBlank(message = "Last name required")
    private String lastName;

    @Email(message = "Wrong email format")
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

<<<<<<< Updated upstream
    private Boolean active = false;

//    @OneToMany
//    @JsonIgnore
//    private List<Book> books;
=======
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();

    //    @OneToMany
    //    @JsonIgnore
    //    private List<Book> books;
>>>>>>> Stashed changes

    public User() {

    }

<<<<<<< Updated upstream
    public Long getUserId() {
        return userId;
=======
    public User(String firstName,String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
>>>>>>> Stashed changes
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

<<<<<<< Updated upstream
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
=======
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
>>>>>>> Stashed changes
    }
}

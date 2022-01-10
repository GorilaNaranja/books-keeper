package com.feliopolis.bookskeeper.commons.utils;

import java.util.List;

public class UserInfoResponse {

    private Long id;
    private String firstName;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String firstName, String email, List<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

package com.feliopolis.bookskeeper.authors.requests;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class EditAuthorRequest {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}

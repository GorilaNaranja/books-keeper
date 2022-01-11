package com.feliopolis.bookskeeper.books.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateBookRequest {

    private Long authorId;

    private String name;

    private String description;

    private LocalDate date;

    private String imageUrl;

}

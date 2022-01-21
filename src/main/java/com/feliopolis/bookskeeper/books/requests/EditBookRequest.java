package com.feliopolis.bookskeeper.books.requests;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EditBookRequest {

    private Long authorId;

    private String name;

    private String description;

    private LocalDate date;

    private String imageUrl;

}

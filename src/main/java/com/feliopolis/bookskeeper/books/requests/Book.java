package com.feliopolis.bookskeeper.books.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    private String name;

    private String description;

    private LocalDate date;

    @Column(name = "image_url")
    private String imageUrl;

}

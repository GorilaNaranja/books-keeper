package com.feliopolis.bookskeeper.authors.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity(name = "authors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Author {

    //@Id
    //@GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    //@GenericGenerator(name = "id", strategy = "org.hibernate.id.UUIDGenerator")
    //private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}

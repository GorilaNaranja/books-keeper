package com.feliopolis.bookskeeper;

import com.feliopolis.bookskeeper.models.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SystemTest {

    @Test
    public void createReadDeleteAuthorTest() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3000/api/v1/authors";

        Author author = new Author("Felipe", "Calderon");
        ResponseEntity<Author> entity = restTemplate.postForEntity(url, author, Author.class);

        Author[] authors = restTemplate.getForObject(url, Author[].class);
        Assertions.assertEquals(authors.length, 1);
        Assertions.assertEquals(authors[0].getFirst_name(), "Felipe");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        Assertions.assertEquals(authors.length, 0);

    }

}

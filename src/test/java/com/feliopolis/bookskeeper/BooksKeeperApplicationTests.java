package com.feliopolis.bookskeeper;

import com.feliopolis.bookskeeper.authors.AuthorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest
class BooksKeeperApplicationTests {

    @Autowired
    AuthorController authorController;

    @Test
    void contextLoads() {
        // Assert.assertNotNull(authorController);
        Assert.notNull(authorController);
    }

}

package com.feliopolis.bookskeeper.it;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.roles.RoleRepository;
import com.feliopolis.bookskeeper.users.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest()
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
@AutoConfigureMockMvc
public class BookApiIT {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

//    @MockBean
//    private EntityManager entityManager;

    @Autowired
    private MockMvc mvc;

    private final Faker faker = Faker.instance();

    @Test
    public void getBook() throws Exception {

        Long bookId = faker.random().nextLong();
        var dbBook = Book.builder().id(bookId).name(faker.book().title()).build();
        Optional<Book> expectedDbBook = Optional.of(dbBook);
        when(bookRepository.findById(eq(bookId))).thenReturn(expectedDbBook);

        // HTTP REQUESTS
        // contentType = lo que devuelve el endpoint
        // accept = lo que espera

        mvc.perform(get(String.format("/api/v1/books/%d", bookId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(dbBook.getId()))
                .andExpect(jsonPath("$.name").value(dbBook.getName()));
    }

    // TODO: to learn
    // swagger
    // test driven development
    // validation

}

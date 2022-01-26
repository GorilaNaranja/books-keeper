package com.feliopolis.bookskeeper.authors.services;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.authors.requests.Author;
import com.feliopolis.bookskeeper.authors.requests.CreateAuthorRequest;
import com.feliopolis.bookskeeper.authors.requests.EditAuthorRequest;
import com.feliopolis.bookskeeper.books.BookCache;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.requests.CreateBookRequest;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;
import com.feliopolis.bookskeeper.books.services.BookService;
import com.feliopolis.bookskeeper.commons.utils.NullBeanUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.MatchMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceQuery implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getAuthor(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> getAuthorByFirstName(String firstName) {

//        var cb = entityManager.getCriteriaBuilder();
//        var query = cb.createQuery(Author.class);
//        query.where(cb.like("first_name", "%" + firstName + "%", MatchMode.ANYWHERE));
//        return entityManager.createQuery(query).getResultList();

        return authorRepository.findByFirstNameContainingAllIgnoreCase(firstName);
    }

    @Override
    public Author createAuthor(CreateAuthorRequest createAuthorRequest) throws InvalidBookDataException {

        if (authorRepository.findOneByFirstNameAndLastName(createAuthorRequest.getFirstName(),
                createAuthorRequest.getLastName()).isPresent()) {
            throw new InvalidBookDataException("Author already exist");
        }

        var author = new Author(null,
                createAuthorRequest.getFirstName(),
                createAuthorRequest.getLastName());

        return authorRepository.saveAndFlush(author);
    }

    @Override
    public Author editAuthor(Long id, EditAuthorRequest editAuthorRequest) throws InvalidBookDataException, InvocationTargetException, IllegalAccessException {

        var possibleAuthorDB = authorRepository.findById(id);

        if (possibleAuthorDB.isEmpty()) {
            throw new InvalidBookDataException("Book doesn't exist: " + id);
        }

        var dbAuthor = possibleAuthorDB.get();

        NullBeanUtils.getInstance().copyProperties(dbAuthor, editAuthorRequest);

        authorRepository.save(dbAuthor);

        return dbAuthor;

    }

    @Override
    public Author deleteAuthor(Long id) throws InvalidBookDataException {
        var possibleAuthor = authorRepository.findById(id);

        if (possibleAuthor.isEmpty()) {
            throw new InvalidBookDataException("Author doesn't exist: " + id);
        }

        authorRepository.deleteById(id);

        return possibleAuthor.get();
    }

}

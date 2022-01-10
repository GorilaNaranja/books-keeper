package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> getBooks(Long authorId) {
        if (authorId == null)
            return bookRepository.findAll();
        else
            return bookRepository.findByAuthorId(authorId);
    }

    public Book getBook(Long id) {
        if (bookRepository.findById(id).isPresent())
            return bookRepository.getById(id);
        else
            // TODO: throw exception not working?
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
    }

    public Book createBook(Book book) {
        if (!authorRepository.findById(book.getAuthor().getId()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Not Found");
        else
            return bookRepository.saveAndFlush(book);
    }

    public Book editBook(Long id, Book book) {
        // TODO: 3 queries needed?
        // TODO: require all fields, if not return null
        // TODO: now returns complete author, return only author id
        if (!bookRepository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
        else {
            Book savedBook = bookRepository.getById(id);
            BeanUtils.copyProperties(book, savedBook, "id");
            bookRepository.saveAndFlush(savedBook);
            return savedBook;
        }
    }

    public Book deleteBook(Long id) {
        // TODO: 3 queries needed?
        if (bookRepository.findById(id).isPresent()) {
            Book book = bookRepository.getById(id);
            bookRepository.deleteById(id);
            return book;
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }


}

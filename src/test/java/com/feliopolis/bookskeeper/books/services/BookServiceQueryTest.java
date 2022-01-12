package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.requests.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceQueryTest {

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private AuthorRepository mockAuthorRepository;

    @InjectMocks
    private BookServiceQuery serviceToTest;

    @Test
    public void getBook() {
        Long id = 1L;
        Optional<Book> expectedResult = Optional.empty();
        when(mockBookRepository.findById(eq(id))).thenReturn(expectedResult);
        var result = serviceToTest.getBook(id);
        assertThat(result).isSameAs(expectedResult);
        verify(mockBookRepository, times(1)).findById(eq(id));
    }

    @Test
    public void getBooksByAuthorId() {
        Long authorId = 1L;
        List<Book> expectedResult = List.of();
        when(mockBookRepository.findByAuthorId(eq(authorId))).thenReturn(expectedResult);
        List<Book> result = serviceToTest.getBooks(authorId);
        assertThat(result).isSameAs(expectedResult);
        verify(mockBookRepository, times(1)).findByAuthorId(eq(authorId));
    }

    @Test
    public void getBooksWithNullAuthorId() {
        List<Book> expectedResult = List.of();
        when(mockBookRepository.findAll()).thenReturn(expectedResult);
        List<Book> result = serviceToTest.getBooks(null);
        assertThat(result).isSameAs(expectedResult);
        verify(mockBookRepository, times(1)).findAll();
    }

//    @Test
//    public void deleteBookNonExist() throws InvalidBookDataException {
//        Long id = 1L;
//        when(mockBookRepository.findById(eq(id))).thenReturn(null);
//        Book result = serviceToTest.deleteBook(eq(id));
//        assertThat(result).isSameAs(new InvalidBookDataException("Book doesn't exist: " + id));
//        verify(mockBookRepository, times(1)).deleteById(eq(id));
//    }

}
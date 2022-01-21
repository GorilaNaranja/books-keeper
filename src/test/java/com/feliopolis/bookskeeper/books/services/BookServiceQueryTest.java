package com.feliopolis.bookskeeper.books.services;

import com.feliopolis.bookskeeper.authors.Author;
import com.feliopolis.bookskeeper.authors.AuthorRepository;
import com.feliopolis.bookskeeper.books.BookCache;
import com.feliopolis.bookskeeper.books.BookRepository;
import com.feliopolis.bookskeeper.books.InvalidBookDataException;
import com.feliopolis.bookskeeper.books.requests.Book;
import com.feliopolis.bookskeeper.books.requests.EditBookRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceQueryTest {

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private AuthorRepository mockAuthorRepository;

    @Mock
    private BookCache bookCache;

    @InjectMocks
    private BookServiceQuery serviceToTest;

    @Test
    public void getBook() {
        Long id = 1L;
        Optional<Book> expectedResult = Optional.empty();
        when(bookCache.findById(eq(id))).thenReturn(expectedResult);
        var result = serviceToTest.getBook(id);
        assertThat(result).isSameAs(expectedResult);
        verify(bookCache, times(1)).findById(eq(id));
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

    @Test
    public void editBook() throws InvocationTargetException, IllegalAccessException, InvalidBookDataException {

        var dbBook = Book.builder().id(1L).authorId(4L).name("Book 1").description("Description 1").build();
        var editBookRequest = EditBookRequest.builder().authorId(4L).name("Edited Name").build();
        var expectedBook = Book.builder().id(1L).authorId(4L).name("Edited Name").description("Description 1").build();
        var author = new Author(4L, "Isaac", "Asimov");

        when(bookCache.findById(eq(dbBook.getId()))).thenReturn(Optional.of(dbBook));
        when(mockAuthorRepository.findById(eq(dbBook.getAuthorId()))).thenReturn(Optional.of(author));

        var result = serviceToTest.editBook(dbBook.getId(), editBookRequest);

        assertThat(result).isEqualTo(expectedBook);
        verify(bookCache, times(1)).findById(eq(dbBook.getId()));
        verify(mockAuthorRepository, times(1)).findById(eq(dbBook.getAuthorId()));
    }

//    @Test
//    public void editBookNonExist() throws InvocationTargetException, IllegalAccessException, InvalidBookDataException {
//        Long id = 1L;
////        var editBookRequest = EditBookRequest.builder().authorId(4L).name("Edited Name").build();
//        when(bookCache.findById(eq(id))).thenThrow(InvalidBookDataException.class);
////        serviceToTest.editBook(id, editBookRequest);
//
////        assertThatThrownBy(() -> bookCache.findById(eq(id)))
////                .isInstanceOf(InvalidBookDataException.class)
////                .hasMessage("Book doesn't exist: 1")
////                .hasNoCause();
////        verify(bookCache, times(1)).findById(eq(id));
//    }

//    @Test
//    public void deleteBookNonExist() throws InvalidBookDataException {
//        Long id = 1L;
//        Optional<Book> expectedResult = Optional.empty();
//        when(mockBookRepository.findById(eq(id))).thenReturn(expectedResult);
//        Book result = serviceToTest.deleteBook(eq(id));
//        assertThat(result).isSameAs(new InvalidBookDataException("Book doesn't exist: " + id));
//        verify(mockBookRepository, times(1)).deleteById(eq(id));
//    }

}
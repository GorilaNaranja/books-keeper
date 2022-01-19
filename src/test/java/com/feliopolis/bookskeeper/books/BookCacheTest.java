package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.books.requests.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCacheTest {

    @Mock
    private BookRepository mockBookRepository;

    @InjectMocks
    private BookCache cache;

    @Test
    void findById() {

        Long bookId = 1L;
        var dbBook = new Book();
        Optional<Book> expectedDbBook = Optional.of(dbBook);
        when(mockBookRepository.findById(eq(bookId))).thenReturn(expectedDbBook);

        Optional<Book> book = cache.findById(bookId);
        assertThat(book).isSameAs(expectedDbBook);

        Optional<Book> book2 = cache.findById(bookId);
        assertThat(book2).isEqualTo(expectedDbBook);

        verify(mockBookRepository, times(1)).findById(eq(bookId));

    }

    @Test
    void deleteById() {

        Long bookId = 1L;
        Book dbBook = new Book();
        Optional<Book> expectedDbBook = Optional.of(dbBook);
        when(mockBookRepository.findById(eq(bookId))).thenReturn(expectedDbBook);

        Optional<Book> book = cache.findById(bookId);
        assertThat(book).isEqualTo(expectedDbBook);

        cache.deleteById(bookId);

        book = cache.findById(bookId);
        assertThat(book).isEqualTo(expectedDbBook);

        verify(mockBookRepository, times(2)).findById(eq(bookId));

    }

    @Test
    void cachePassivation() {

        // TODO: fix test

        var books = LongStream.range(0, 100)
                .mapToObj(id -> Book.builder().id(id).build())
                .collect(Collectors.toList());

        books.forEach(book -> when(mockBookRepository.findById(eq(book.getId()))).thenReturn(Optional.of(book)));

        books.forEach(book -> cache.findById(book.getId()));
        books.forEach(book -> cache.findById(book.getId()));

        LongStream.range(0, 10)
                .forEach(id -> {
                    verify(mockBookRepository, times(2)).findById(eq(id));
                });

        LongStream.range(11, 99)
                .forEach(id -> {
                    verify(mockBookRepository, times(1)).findById(eq(id));
                });

    }
}
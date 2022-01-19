package com.feliopolis.bookskeeper.books;

import com.feliopolis.bookskeeper.books.requests.Book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookCache {

    private final BookRepository bookRepository;

    private static int cacheLimit = 100;
    private static int cacheQuantityToRefresh = 10;

    // Thread safe
    private final LinkedHashMap<Long, Book> cacheById = new LinkedHashMap<>();

    public Optional<Book> findById(Long id) {

        Book possibleBook = cacheById.get(id);

        if (possibleBook != null) { // Book already saved in cache
            return Optional.of(possibleBook);
        }

        Optional<Book> possibleDBBook = bookRepository.findById(id);

        if (possibleDBBook.isPresent()) { // Book from DB

            Book dbBook = possibleDBBook.get();

            synchronized (cacheById) {

                cacheById.put(id, dbBook);

                if (cacheById.size() >= cacheLimit) { // Clear 100 items from cache
                    var keys = new ArrayList<>(cacheById.keySet());
                    var keysToDelete = keys.subList(0, cacheQuantityToRefresh);
                    keysToDelete.forEach(cacheById::remove);
                }
            }

            return possibleDBBook;
        }

        return Optional.empty();

    }

    public synchronized void deleteById(Long id) {
        cacheById.remove(id);
    }
}

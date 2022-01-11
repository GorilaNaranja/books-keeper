package com.feliopolis.bookskeeper.books;

public class InvalidBookDataException extends Exception {
    public InvalidBookDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBookDataException(Throwable cause) {
        super(cause);
    }

    public InvalidBookDataException(String message) {
        super(message);
    }
}

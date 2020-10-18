package org.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public class LibraryException extends Exception {

    @Getter
    private final HttpStatus status;

    public LibraryException() {
        super();
        this.status = INTERNAL_SERVER_ERROR;
    }

    public LibraryException(String message) {
        super(message);
        this.status = INTERNAL_SERVER_ERROR;
    }

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
        this.status = INTERNAL_SERVER_ERROR;
    }

    public LibraryException(Throwable cause) {
        super(cause);
        this.status = INTERNAL_SERVER_ERROR;
    }

    public LibraryException(HttpStatus status) {
        super();
        this.status = status;
    }

    public LibraryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public LibraryException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public LibraryException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}

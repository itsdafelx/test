package edu.bbte.idde.boim2218.backend.exception;

public class CreateEventException extends RuntimeException {
    public CreateEventException(String message) {
        super(message);
    }

    public CreateEventException(String message, Throwable cause) {
        super(message, cause);
    }
}

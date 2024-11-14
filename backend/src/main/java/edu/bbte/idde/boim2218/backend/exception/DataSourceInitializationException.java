package edu.bbte.idde.boim2218.backend.exception;

public class DataSourceInitializationException extends RuntimeException {
    public DataSourceInitializationException(String message) {
        super(message);
    }

    public DataSourceInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

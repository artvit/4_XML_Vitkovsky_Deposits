package by.epam.banks.exception;

public class SchemaCreationException extends Exception {
    public SchemaCreationException() {
    }

    public SchemaCreationException(String message) {
        super(message);
    }

    public SchemaCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaCreationException(Throwable cause) {
        super(cause);
    }
}

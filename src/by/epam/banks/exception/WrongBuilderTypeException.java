package by.epam.banks.exception;

public class WrongBuilderTypeException extends Exception {
    public WrongBuilderTypeException() {
    }

    public WrongBuilderTypeException(String message) {
        super(message);
    }

    public WrongBuilderTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongBuilderTypeException(Throwable cause) {
        super(cause);
    }
}

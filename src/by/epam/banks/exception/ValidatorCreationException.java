package by.epam.banks.exception;

public class ValidatorCreationException extends Exception {
    public ValidatorCreationException() {
    }

    public ValidatorCreationException(String message) {
        super(message);
    }

    public ValidatorCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorCreationException(Throwable cause) {
        super(cause);
    }
}

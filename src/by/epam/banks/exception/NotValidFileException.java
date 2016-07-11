package by.epam.banks.exception;

/**
 * Created by artvi on 22/06/2016.
 */
public class NotValidFileException extends Exception {
    public NotValidFileException() {
    }

    public NotValidFileException(String message) {
        super(message);
    }

    public NotValidFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidFileException(Throwable cause) {
        super(cause);
    }
}

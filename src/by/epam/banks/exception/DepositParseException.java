package by.epam.banks.exception;

/**
 * Created by artvi on 22/06/2016.
 */
public class DepositParseException extends Exception {
    public DepositParseException() {
    }

    public DepositParseException(String message) {
        super(message);
    }

    public DepositParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositParseException(Throwable cause) {
        super(cause);
    }
}

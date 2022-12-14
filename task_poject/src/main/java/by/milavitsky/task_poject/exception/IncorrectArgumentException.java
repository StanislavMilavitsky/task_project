package by.milavitsky.task_poject.exception;

public class IncorrectArgumentException extends Exception {

    public IncorrectArgumentException() {
        super();
    }

    public IncorrectArgumentException(String message) {
        super(message);
    }

    public IncorrectArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectArgumentException(Throwable cause) {
        super(cause);
    }
}
package pt.sensae.services.data.gateway.application;

public class NotValidException extends RuntimeException {

    private final ExceptionDetail error;

    public NotValidException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }

    public Exception getException() {
        return this;
    }
}

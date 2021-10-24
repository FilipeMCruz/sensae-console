package pt.sharespot.services.lgt92gpssensorprocessor.model.exceptions;

public abstract class DomainException extends RuntimeException {

    private final ExceptionDetail error;

    public DomainException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }
}

package pt.sensae.services.data.processor.master.backend.domain.exceptions;

public class InvalidTransformationException extends RuntimeException {

    private final ExceptionDetail error;

    public InvalidTransformationException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }
}

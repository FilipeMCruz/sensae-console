package pt.sensae.services.data.decoder.master.backend.domain.exceptions;

public class InvalidDecoderException extends RuntimeException {

    private final ExceptionDetail error;

    public InvalidDecoderException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }
}

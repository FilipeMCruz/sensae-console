package pt.sensae.services.data.processor.master.backend.application.auth;

import pt.sensae.services.data.processor.master.backend.domain.exceptions.ExceptionDetail;

public class UnauthorizedException extends RuntimeException {

    private final ExceptionDetail error;

    public UnauthorizedException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }
}

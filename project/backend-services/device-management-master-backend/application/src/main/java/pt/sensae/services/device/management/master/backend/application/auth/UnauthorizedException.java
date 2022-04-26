package pt.sensae.services.device.management.master.backend.application.auth;

import pt.sensae.services.device.management.master.backend.domain.model.exceptions.ExceptionDetail;

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

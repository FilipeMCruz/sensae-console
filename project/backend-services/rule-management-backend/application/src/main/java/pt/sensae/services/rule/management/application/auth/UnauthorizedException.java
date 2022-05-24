package pt.sensae.services.rule.management.application.auth;

import pt.sensae.services.rule.management.domain.exceptions.ExceptionDetail;

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

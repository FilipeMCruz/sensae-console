package pt.sensae.services.rule.management.application.auth;

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
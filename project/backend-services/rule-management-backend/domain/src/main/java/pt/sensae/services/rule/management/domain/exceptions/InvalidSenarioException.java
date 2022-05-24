package pt.sensae.services.rule.management.domain.exceptions;


public class InvalidSenarioException extends RuntimeException {

    private final ExceptionDetail error;

    public InvalidSenarioException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }
}

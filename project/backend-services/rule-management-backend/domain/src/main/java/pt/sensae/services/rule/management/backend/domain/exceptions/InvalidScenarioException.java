package pt.sensae.services.rule.management.backend.domain.exceptions;


public class InvalidScenarioException extends RuntimeException {

    private final ExceptionDetail error;

    public InvalidScenarioException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }
}

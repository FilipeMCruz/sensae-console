package pt.sensae.services.rule.management.backend.domain.exceptions;

public class ExceptionDetail {

    String details;

    private ExceptionDetail(String details) {
        this.details = details;
    }

    public ExceptionDetail() {
    }

    public static ExceptionDetail of(String error) {
        return new ExceptionDetail(error);
    }

    public String getDetails() {
        return details;
    }
}

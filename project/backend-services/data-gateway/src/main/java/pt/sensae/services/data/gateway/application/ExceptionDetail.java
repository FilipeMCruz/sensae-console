package pt.sensae.services.data.gateway.application;

public class ExceptionDetail {

    public String error;

    private ExceptionDetail(String error) {
        this.error = error;
    }

    public ExceptionDetail() {
    }

    public static ExceptionDetail of(String error) {
        return new ExceptionDetail(error);
    }

    public String getError() {
        return error;
    }
}

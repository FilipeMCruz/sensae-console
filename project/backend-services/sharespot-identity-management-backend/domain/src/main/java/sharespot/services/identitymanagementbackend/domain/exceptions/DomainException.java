package sharespot.services.identitymanagementbackend.domain.exceptions;

import java.util.function.Supplier;

public abstract class DomainException extends RuntimeException {

    private final ExceptionDetail error;

    public DomainException(String message) {
        super(message);
        this.error = ExceptionDetail.of(message);
    }

    public ExceptionDetail getError() {
        return error;
    }

    public static Supplier<DomainException> withMessage(String message) {
        return () -> new NotValidException(message);
    }
}

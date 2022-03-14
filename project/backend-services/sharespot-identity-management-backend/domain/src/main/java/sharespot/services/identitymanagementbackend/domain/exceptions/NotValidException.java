package sharespot.services.identitymanagementbackend.domain.exceptions;

import java.util.function.Supplier;

public class NotValidException extends DomainException {

    public NotValidException(String message) {
        super(message);
    }

    public static Supplier<NotValidException> withMessage(String message) {
        return () -> new NotValidException(message);
    }
}

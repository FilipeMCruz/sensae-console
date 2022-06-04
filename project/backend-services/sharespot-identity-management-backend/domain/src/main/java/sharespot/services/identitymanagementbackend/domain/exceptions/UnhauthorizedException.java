package sharespot.services.identitymanagementbackend.domain.exceptions;

import java.util.function.Supplier;

public class UnhauthorizedException extends DomainException {

    public UnhauthorizedException(String message) {
        super(message);
    }

    public static Supplier<UnhauthorizedException> withMessage(String message) {
        return () -> new UnhauthorizedException(message);
    }
}

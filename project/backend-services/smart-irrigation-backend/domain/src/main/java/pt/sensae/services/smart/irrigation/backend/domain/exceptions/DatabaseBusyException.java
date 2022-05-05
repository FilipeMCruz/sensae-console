package pt.sensae.services.smart.irrigation.backend.domain.exceptions;

public class DatabaseBusyException extends DomainException {

    public DatabaseBusyException(String message) {
        super(message);
    }

}

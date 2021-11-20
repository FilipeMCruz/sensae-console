package sharespot.services.locationtrackingbackend.application.exceptions;

import sharespot.services.locationtrackingbackend.domain.exceptions.DomainException;

public class PublishErrorException extends DomainException {

    public PublishErrorException(String message) {
        super(message);
    }
}

package pt.sharespot.services.locationtrackingbackend.application.exceptions;

import pt.sharespot.services.locationtrackingbackend.domain.exceptions.DomainException;

public class PublishErrorException extends DomainException {

    public PublishErrorException(String message) {
        super(message);
    }
}

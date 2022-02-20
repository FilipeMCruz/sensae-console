package sharespot.services.identitymanagementbackend.domain.identity.domain;

import java.util.UUID;

public record DomainId(UUID value) {

    public static DomainId of(UUID value) {
        return new DomainId(value);
    }
}

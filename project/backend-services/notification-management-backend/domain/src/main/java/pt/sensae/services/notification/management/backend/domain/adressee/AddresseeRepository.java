package pt.sensae.services.notification.management.backend.domain.adressee;

import java.util.Optional;

public interface AddresseeRepository {

    Optional<Addressee> findById(AddresseeId id);

    Addressee index(Addressee addressee);
}

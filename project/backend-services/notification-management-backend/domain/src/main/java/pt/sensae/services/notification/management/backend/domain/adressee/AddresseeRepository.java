package pt.sensae.services.notification.management.backend.domain.adressee;

import java.util.Set;
import java.util.stream.Stream;

public interface AddresseeRepository {

    Addressee findById(AddresseeId id);

    Stream<Addressee> findAll();

    Addressee index(Addressee addressee);

    void update(Set<Addressee> configUpdates);
}

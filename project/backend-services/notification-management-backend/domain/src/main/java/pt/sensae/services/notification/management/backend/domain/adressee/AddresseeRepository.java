package pt.sensae.services.notification.management.backend.domain.adressee;

import java.util.stream.Stream;

public interface AddresseeRepository {

    Stream<Addressee> findById(Stream<AddresseeId> id);
}

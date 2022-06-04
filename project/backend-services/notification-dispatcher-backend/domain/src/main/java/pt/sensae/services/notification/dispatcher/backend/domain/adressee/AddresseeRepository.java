package pt.sensae.services.notification.dispatcher.backend.domain.adressee;

import java.util.Set;

public interface AddresseeRepository {

    Addressee findById(AddresseeId id);

    void index(Addressee addressee);

    void refresh(Set<Addressee> adrAddressees);
}

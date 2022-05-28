package pt.sensae.services.notification.management.backend.domain.adressee;

public interface AddresseeRepository {

    Addressee findById(AddresseeId id);

    Addressee index(Addressee addressee);
}

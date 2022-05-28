package pt.sensae.services.notification.management.backend.application.addressee.mapper;

import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeConfigUpdateCommand;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;

public interface AddresseeCommandMapper {

    Addressee toDomain(AddresseeConfigUpdateCommand model, AddresseeId id);
}

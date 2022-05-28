package pt.sensae.services.notification.management.backend.application.addressee.mapper;

import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeConfigUpdateCommandDTO;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;

public interface AddresseeCommandMapper {

    Addressee toDomain(AddresseeConfigUpdateCommandDTO dto, AddresseeId id);
}

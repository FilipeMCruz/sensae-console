package pt.sensae.services.notification.management.backend.application.addressee.mapper;

import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;

public interface AddresseeDTOMapper {

    AddresseeDTO toDto(Addressee model);
}

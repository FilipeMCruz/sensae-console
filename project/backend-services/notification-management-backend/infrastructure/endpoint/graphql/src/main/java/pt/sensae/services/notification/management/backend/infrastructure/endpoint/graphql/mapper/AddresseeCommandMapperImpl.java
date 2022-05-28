package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeCommandMapper;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeConfigUpdateCommandDTO;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeConfig;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.*;

import java.util.stream.Collectors;

@Service
public class AddresseeCommandMapperImpl implements AddresseeCommandMapper {
    @Override
    public Addressee toDomain(AddresseeConfigUpdateCommandDTO dto, AddresseeId id) {
        var in = (AddresseeConfigUpdateCommandDTOImpl) dto;
        var addresseeConfigs = in.entries.stream().map(entry -> {
            var contentType = ContentType.of(entry.contentType.category, entry.contentType.subCategory, NotificationLevelMapper.extract(entry.contentType.level));
            return AddresseeConfig.of(contentType, extract(entry.deliveryType), entry.mute);
        }).collect(Collectors.toSet());

        return Addressee.of(id, addresseeConfigs);
    }

    private DeliveryType extract(DeliveryTypeDTOImpl deliveryType) {
        return switch (deliveryType) {
            case NOTIFICATION -> DeliveryType.NOTIFICATION;
            case SMS -> DeliveryType.SMS;
            case EMAIL -> DeliveryType.EMAIL;
        };
    }
}

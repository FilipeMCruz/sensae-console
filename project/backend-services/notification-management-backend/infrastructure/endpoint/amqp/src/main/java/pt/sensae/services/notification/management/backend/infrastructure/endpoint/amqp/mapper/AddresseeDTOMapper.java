package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.InternalAddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.AddresseeConfigDTO;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.AddresseeDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.DeliveryTypeDTO;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model.NotificationLevelDTO;

import java.util.stream.Collectors;

@Component
public class AddresseeDTOMapper implements InternalAddresseeDTOMapper {

    public AddresseeDTO domainToDto(Addressee model) {
        var dto = new AddresseeDTOImpl();

        dto.entries = model.configs().stream().map(entry -> {
            var config = new AddresseeConfigDTO();
            config.category = entry.contentType().category();
            config.subCategory = entry.contentType().subCategory();
            config.severity = extract(entry.contentType().level());

            config.mute = entry.mute();
            config.deliveryType = extract(entry.deliveryType());

            return config;
        }).collect(Collectors.toSet());

        dto.id = model.id().value().toString();

        return dto;
    }

    private DeliveryTypeDTO extract(DeliveryType deliveryType) {
        return switch (deliveryType) {
            case NOTIFICATION -> DeliveryTypeDTO.NOTIFICATION;
            case SMS -> DeliveryTypeDTO.SMS;
            case EMAIL -> DeliveryTypeDTO.EMAIL;
            case UI -> DeliveryTypeDTO.UI;
        };
    }

    private NotificationLevelDTO extract(NotificationLevel level) {
        return switch (level) {
            case INFORMATION -> NotificationLevelDTO.INFORMATION;
            case WATCH -> NotificationLevelDTO.WATCH;
            case ADVISORY -> NotificationLevelDTO.ADVISORY;
            case WARNING -> NotificationLevelDTO.WARNING;
            case CRITICAL -> NotificationLevelDTO.CRITICAL;
        };
    }
}

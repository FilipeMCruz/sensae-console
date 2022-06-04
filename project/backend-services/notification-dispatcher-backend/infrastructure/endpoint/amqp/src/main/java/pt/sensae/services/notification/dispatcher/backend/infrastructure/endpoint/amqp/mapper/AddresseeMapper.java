package pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.mapper;

import pt.sensae.services.notification.dispatcher.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeConfig;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.dispatcher.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.dispatcher.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.AddresseeDTO;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.DeliveryTypeDTO;
import pt.sensae.services.notification.dispatcher.backend.infrastructure.endpoint.amqp.model.NotificationLevelDTO;

import java.util.UUID;
import java.util.stream.Collectors;

public class AddresseeMapper {

    public static Addressee dtoToDomain(AddresseeDTO addressee) {
        var id = new AddresseeId(UUID.fromString(addressee.id));
        var entries = addressee.entries.stream().map(entry -> {
            var contentType = new ContentType(entry.category, entry.subCategory, extract(entry.severity));
            return new AddresseeConfig(contentType, extract(entry.deliveryType), entry.mute);
        }).collect(Collectors.toSet());
        return Addressee.of(id, entries);
    }

    private static NotificationLevel extract(NotificationLevelDTO dto) {
        return switch (dto) {
            case WATCH -> NotificationLevel.WATCH;
            case INFORMATION -> NotificationLevel.INFORMATION;
            case ADVISORY -> NotificationLevel.ADVISORY;
            case WARNING -> NotificationLevel.WARNING;
            case CRITICAL -> NotificationLevel.CRITICAL;
        };
    }

    private static DeliveryType extract(DeliveryTypeDTO dto) {
        return switch (dto) {
            case EMAIL -> DeliveryType.EMAIL;
            case NOTIFICATION -> DeliveryType.NOTIFICATION;
            case SMS -> DeliveryType.SMS;
        };
    }
}

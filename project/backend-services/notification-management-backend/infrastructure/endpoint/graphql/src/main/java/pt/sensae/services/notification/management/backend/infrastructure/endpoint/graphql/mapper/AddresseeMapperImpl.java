package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.addressee.mapper.AddresseeDTOMapper;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.*;

import java.util.stream.Collectors;

@Service
public class AddresseeMapperImpl implements AddresseeDTOMapper {

    @Override
    public AddresseeDTO toDto(Addressee model) {
        var dto = new AddresseeDTOImpl();

        dto.entries = model.configs().stream().map(entry -> {
            var contentType = new ContentTypeDTOImpl();
            contentType.category = entry.contentType().category();
            contentType.subCategory = entry.contentType().subCategory();
            contentType.level = NotificationLevelMapper.extract(entry.contentType().level());

            var config = new AddresseeConfigDTOImpl();
            config.contentType = contentType;
            config.mute = entry.mute();
            config.deliveryType = extract(entry.deliveryType());

            return config;
        }).collect(Collectors.toSet());

        return dto;
    }

    private DeliveryTypeDTOImpl extract(DeliveryType deliveryType) {
        return switch (deliveryType) {
            case NOTIFICATION -> DeliveryTypeDTOImpl.NOTIFICATION;
            case SMS -> DeliveryTypeDTOImpl.SMS;
            case EMAIL -> DeliveryTypeDTOImpl.EMAIL;
        };
    }
}

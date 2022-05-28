package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.addressee;

import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeConfig;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.DeliveryType;
import pt.sensae.services.notification.management.backend.domain.contentType.ContentType;
import pt.sensae.services.notification.management.backend.domain.notification.NotificationLevel;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.addressee.AddresseePostgres;

import java.util.HashSet;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddresseeMapper {

    public static Stream<AddresseePostgres> modelToDao(Addressee model) {
        return model.configs()
                .stream()
                .collect(Collectors.groupingBy(AddresseeConfig::contentType))
                .values()
                .stream()
                .map(contentTypList -> {
                    var addresseePostgres = new AddresseePostgres();
                    addresseePostgres.id = model.id().toString();
                    addresseePostgres.category = contentTypList.get(0).contentType().category();
                    addresseePostgres.subCategory = contentTypList.get(0).contentType().subCategory();
                    addresseePostgres.level = contentTypList.get(0)
                            .contentType()
                            .level()
                            .name()
                            .toLowerCase(Locale.ROOT);
                    addresseePostgres.sendEmail = contentTypList.stream()
                            .filter(conf -> conf.deliveryType().equals(DeliveryType.EMAIL))
                            .findFirst()
                            .map(c -> !c.mute())
                            .orElse(false);
                    addresseePostgres.sendSms = contentTypList.stream()
                            .filter(conf -> conf.deliveryType().equals(DeliveryType.SMS))
                            .findFirst()
                            .map(c -> !c.mute())
                            .orElse(false);
                    addresseePostgres.sendNotification = contentTypList.stream()
                            .filter(conf -> conf.deliveryType().equals(DeliveryType.NOTIFICATION))
                            .findFirst()
                            .map(c -> !c.mute())
                            .orElse(false);
                    return addresseePostgres;
                });
    }

    public static Stream<Addressee> daoToModel(Stream<AddresseePostgres> postgres) {
        return postgres.collect(Collectors.groupingBy(s -> s.id)).values().stream().map(addresseeConfigs -> {
            var id = AddresseeId.of(UUID.fromString(addresseeConfigs.get(0).id));
            var configs = new HashSet<AddresseeConfig>();
            addresseeConfigs.forEach(conf -> {
                var contentType = new ContentType(conf.category, conf.subCategory, NotificationLevel.valueOf(conf.level));
                configs.add(AddresseeConfig.of(contentType, DeliveryType.EMAIL, !conf.sendEmail));
                configs.add(AddresseeConfig.of(contentType, DeliveryType.SMS, !conf.sendSms));
                configs.add(AddresseeConfig.of(contentType, DeliveryType.NOTIFICATION, !conf.sendNotification));
            });
            return Addressee.of(id, configs);
        });
    }
}

package pt.sensae.services.notification.dispatcher.backend.domain.adressee;

import pt.sensae.services.notification.dispatcher.backend.domain.notification.Notification;

import java.util.Set;

public record Addressee(AddresseeId id, Set<AddresseeConfig> configs) {

    public boolean canSendVia(Notification notification, DeliveryType deliveryType) {
        if (deliveryType.equals(DeliveryType.NOTIFICATION)) {
            if (configs.stream().noneMatch(config -> config.contentType().equals(notification.type()))) {
                return true;
            }
        }

        return configs.stream()
                .filter(config -> config.contentType().equals(notification.type()))
                .filter(addresseeConfig -> !addresseeConfig.mute())
                .map(AddresseeConfig::deliveryType)
                .anyMatch(d -> d.equals(deliveryType));
    }

    public static Addressee of(AddresseeId id, Set<AddresseeConfig> configs) {
        return new Addressee(id, configs);
    }
}

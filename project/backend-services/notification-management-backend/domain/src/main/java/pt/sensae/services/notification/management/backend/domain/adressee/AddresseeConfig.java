package pt.sensae.services.notification.management.backend.domain.adressee;

import pt.sensae.services.notification.management.backend.domain.notification.ContentType;

public record AddresseeConfig(ContentType contentType, DeliveryType deliveryType, Boolean mute) {
    public static AddresseeConfig of(ContentType contentType, DeliveryType deliveryType, Boolean mute) {
        return new AddresseeConfig(contentType, deliveryType, mute);
    }
}

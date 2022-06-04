package pt.sensae.services.notification.dispatcher.backend.domain.adressee;

import pt.sensae.services.notification.dispatcher.backend.domain.contentType.ContentType;

public record AddresseeConfig(ContentType contentType, DeliveryType deliveryType, Boolean mute) {
    public static AddresseeConfig of(ContentType contentType, DeliveryType deliveryType, Boolean mute) {
        return new AddresseeConfig(contentType, deliveryType, mute);
    }
}

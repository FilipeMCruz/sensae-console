package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.model;

public class AddresseeConfigDTO {

    public boolean mute;

    public DeliveryTypeDTO deliveryType;

    public String category;

    public String subCategory;

    public NotificationLevelDTO severity;
}

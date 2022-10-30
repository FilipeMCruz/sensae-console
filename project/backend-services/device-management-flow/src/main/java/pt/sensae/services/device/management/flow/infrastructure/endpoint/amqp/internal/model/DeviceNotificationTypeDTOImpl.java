package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum DeviceNotificationTypeDTOImpl {
    UPDATE,
    DELETE
}

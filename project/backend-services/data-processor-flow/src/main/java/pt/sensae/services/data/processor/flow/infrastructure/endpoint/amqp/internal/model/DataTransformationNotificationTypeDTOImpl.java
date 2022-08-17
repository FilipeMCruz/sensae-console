package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum DataTransformationNotificationTypeDTOImpl {
    UPDATE,
    DELETE
}

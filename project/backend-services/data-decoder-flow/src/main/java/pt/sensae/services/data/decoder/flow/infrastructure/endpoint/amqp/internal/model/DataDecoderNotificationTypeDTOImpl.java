package pt.sensae.services.data.decoder.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum DataDecoderNotificationTypeDTOImpl {
    UPDATE,
    DELETE
}

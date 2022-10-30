package pt.sensae.services.data.decoder.flow.application.model;

import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record SensorTypeTopicMessage(SensorTypeId type, InternalRoutingKeys keys) {
}

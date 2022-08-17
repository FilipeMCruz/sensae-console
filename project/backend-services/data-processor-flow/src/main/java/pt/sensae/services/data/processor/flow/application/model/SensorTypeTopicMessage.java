package pt.sensae.services.data.processor.flow.application.model;

import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record SensorTypeTopicMessage(SensorTypeId type, InternalRoutingKeys keys) {
}

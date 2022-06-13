package pt.sensae.services.data.processor.slave.backend.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sensae.services.data.processor.slave.backend.domain.SensorTypeId;

public record SensorTypeTopicMessage(SensorTypeId type, InternalRoutingKeys keys) {
}

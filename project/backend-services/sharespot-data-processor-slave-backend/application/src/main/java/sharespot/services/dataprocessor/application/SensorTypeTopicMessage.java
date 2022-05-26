package sharespot.services.dataprocessor.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import sharespot.services.dataprocessor.domain.SensorTypeId;

public record SensorTypeTopicMessage(SensorTypeId type, InternalRoutingKeys keys) {
}

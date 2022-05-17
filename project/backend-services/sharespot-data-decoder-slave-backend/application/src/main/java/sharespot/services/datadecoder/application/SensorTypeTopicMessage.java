package sharespot.services.datadecoder.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import sharespot.services.datadecoder.domain.SensorTypeId;

public record SensorTypeTopicMessage(SensorTypeId type, InternalRoutingKeys keys) {
}

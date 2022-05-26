package sharespot.services.data.decoder.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import sharespot.services.data.decoder.domain.SensorTypeId;

public record SensorTypeTopicMessage(SensorTypeId type, InternalRoutingKeys keys) {
}

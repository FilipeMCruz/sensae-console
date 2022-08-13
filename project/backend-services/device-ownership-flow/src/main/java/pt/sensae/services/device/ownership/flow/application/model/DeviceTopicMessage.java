package pt.sensae.services.device.ownership.flow.application.model;

import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record DeviceTopicMessage(DeviceId device, InternalRoutingKeys keys) {
}

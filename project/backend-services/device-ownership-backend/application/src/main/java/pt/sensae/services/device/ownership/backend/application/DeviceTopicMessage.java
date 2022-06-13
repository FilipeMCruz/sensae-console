package pt.sensae.services.device.ownership.backend.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;

public record DeviceTopicMessage(DeviceId device, InternalRoutingKeys keys) {
}

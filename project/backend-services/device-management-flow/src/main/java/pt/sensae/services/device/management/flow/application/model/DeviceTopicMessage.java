package pt.sensae.services.device.management.flow.application.model;

import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record DeviceTopicMessage(Device device, InternalRoutingKeys keys) {
}

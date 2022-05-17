package pt.sensae.services.device.management.slave.backend.application;

import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record DeviceTopicMessage(Device device, InternalRoutingKeys keys) {
}

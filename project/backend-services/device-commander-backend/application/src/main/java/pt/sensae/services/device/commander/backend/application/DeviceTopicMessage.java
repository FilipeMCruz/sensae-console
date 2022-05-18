package pt.sensae.services.device.commander.backend.application;

import pt.sensae.services.device.commander.backend.domain.model.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record DeviceTopicMessage(Device device, InternalRoutingKeys keys) {
}

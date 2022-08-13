package pt.sensae.services.device.commander.application.model;

import pt.sensae.services.device.commander.domain.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;

public record DeviceTopicMessage(Device device, InternalRoutingKeys keys) {
}

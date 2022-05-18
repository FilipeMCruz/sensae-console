package sharespot.services.identitymanagementslavebackend.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;

public record DeviceTopicMessage(DeviceId device, InternalRoutingKeys keys) {
}

package pt.sensae.services.device.commander.backend.domain.model.subDevices;

import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;

public record SubDevice(DeviceId id, DeviceRef ref) {
}

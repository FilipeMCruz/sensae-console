package pt.sensae.services.device.commander.domain.subDevices;

import pt.sensae.services.device.commander.domain.device.DeviceId;

public record SubDevice(DeviceId id, DeviceRef ref) {
}

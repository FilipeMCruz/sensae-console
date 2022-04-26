package pt.sensae.services.device.management.master.backend.domain.model.subDevices;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

public record SubDevice(DeviceId id, DeviceRef ref) {
}

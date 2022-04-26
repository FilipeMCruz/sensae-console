package pt.sensae.services.device.management.master.backend.domain.model.records;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

public record Device(DeviceId id, DeviceName name) {
}

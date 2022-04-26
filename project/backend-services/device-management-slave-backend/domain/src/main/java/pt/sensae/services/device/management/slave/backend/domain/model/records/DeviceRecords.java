package pt.sensae.services.device.management.slave.backend.domain.model.records;

import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;

public record DeviceRecords(Device device,
                            Records records,
                            SubDevices subDevices) {
}

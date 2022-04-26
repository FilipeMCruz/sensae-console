package pt.sensae.services.device.management.master.backend.domain.model.records;

import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;

public record DeviceRecords(Device device,
                            Records records,
                            SubDevices subDevices) {
}

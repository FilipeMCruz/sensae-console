package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.subDevices.SubDevices;

public record DeviceRecords(Device device,
                            Records records,
                            SubDevices subDevices) {
}

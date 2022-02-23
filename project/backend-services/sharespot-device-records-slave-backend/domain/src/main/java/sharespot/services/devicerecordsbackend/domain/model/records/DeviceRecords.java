package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.Device;

public record DeviceRecords(Device device,
                            Records records) {
}

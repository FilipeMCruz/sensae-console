package pt.sensae.services.smart.irrigation.backend.domain.model.content;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

public record DeviceContent(DeviceName name, DeviceRecords records, GPSPoint coordinates) {
}

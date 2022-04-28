package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

public record DeviceContent(DeviceName name, DeviceRecords records, GPSPoint coordinates, RemoteControl remoteControl) {
}

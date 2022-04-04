package pt.sensae.services.smart.irrigation.backend.domain.model.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.ledger.DeviceLedger;

public record Device(DeviceId id, DeviceType type, DeviceLedger ledger) {
}

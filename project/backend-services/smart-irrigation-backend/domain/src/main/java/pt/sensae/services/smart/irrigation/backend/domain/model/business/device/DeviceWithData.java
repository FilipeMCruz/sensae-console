package pt.sensae.services.smart.irrigation.backend.domain.model.business.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.DeviceLedgerWithData;

public record DeviceWithData(DeviceId id, DeviceType type, DeviceLedgerWithData ledger) {

}

package pt.sensae.services.smart.irrigation.backend.domain.model.business.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.DeviceLedger;

public record Device(DeviceId id, DeviceType type, DeviceLedger ledger) {

    public DeviceWithData toHistory() {
        return new DeviceWithData(id, type, ledger.toHistory());
    }

}

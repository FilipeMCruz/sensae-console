package pt.sensae.services.smart.irrigation.backend.domain.model.business.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.DeviceLedgerWithData;

import java.util.Optional;

public record DeviceWithData(DeviceId id, DeviceType type, DeviceLedgerWithData ledger) {

    public Optional<DeviceWithData> removeEmptyEntries() {
        return ledger.removeEmpty().map(d -> new DeviceWithData(id, type, d));
    }
}

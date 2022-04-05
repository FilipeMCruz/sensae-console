package pt.sensae.services.smart.irrigation.backend.domain.model.business.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query.DeviceQuery;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;

import java.util.Optional;
import java.util.stream.Stream;

public interface DeviceRepository {

    Device add(Device device);

    Optional<LedgerEntry> fetchDeviceActiveLedgerEntry(DeviceId id);

    void openDeviceLedgerEntry(DeviceId id, LedgerEntry entry);

    void closeDeviceLedgerEntry(DeviceId id, CloseDate closeDate);

    Stream<Device> fetchLatest(Ownership ownership);

    Stream<Device> fetch(DeviceQuery query);
}

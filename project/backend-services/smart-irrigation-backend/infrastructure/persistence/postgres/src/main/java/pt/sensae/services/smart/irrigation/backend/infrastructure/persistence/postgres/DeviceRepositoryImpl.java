package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query.DeviceQuery;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {
    @Override
    public Device add(Device device) {
        return device;
    }

    @Override
    public Optional<LedgerEntry> fetchDeviceActiveLedgerEntry(DeviceId id) {
        return Optional.empty();
    }

    @Override
    public void openDeviceLedgerEntry(DeviceId id, LedgerEntry entry) {

    }

    @Override
    public void closeDeviceLedgerEntry(DeviceId id, CloseDate closeDate) {

    }

    @Override
    public Stream<Device> fetchLatest(Ownership ownership) {
        return Stream.empty();
    }

    @Override
    public Stream<Device> fetch(DeviceQuery query) {
        return Stream.empty();
    }
}

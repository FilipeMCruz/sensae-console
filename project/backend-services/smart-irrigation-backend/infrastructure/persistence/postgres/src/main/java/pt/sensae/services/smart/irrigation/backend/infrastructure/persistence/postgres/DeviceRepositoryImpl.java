package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query.DeviceQuery;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.DeviceMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.LedgerMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.RecordsMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.LedgerRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryPostgres deviceRepository;
    private final LedgerRepositoryPostgres ledgerRepository;
    private final RecordsRepositoryPostgres recordsRepository;

    public DeviceRepositoryImpl(DeviceRepositoryPostgres deviceRepository, LedgerRepositoryPostgres ledgerRepository, RecordsRepositoryPostgres recordsRepository) {
        this.deviceRepository = deviceRepository;
        this.ledgerRepository = ledgerRepository;
        this.recordsRepository = recordsRepository;
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Device add(Device device) {
        var devicePostgres = DeviceMapper.modelToDao(device);
        var saved = deviceRepository.save(devicePostgres);

        var ledgerEntry = device.ledger().entries().stream().findFirst().orElseThrow(() -> new NotValidException("A device must be started with a ledger entry"));
        saveLedger(ledgerEntry, saved.deviceId);

        return device;
    }

    @Override
    public Optional<LedgerEntry> fetchDeviceActiveLedgerEntry(DeviceId id) {
        return ledgerRepository.findByDeviceIdAndCloseAtNull(id.value().toString())
                .map(ledgerEntryPostgres -> {
                    var allByEntryPersistenceId = recordsRepository.getAllByEntryPersistenceId(ledgerEntryPostgres.persistenceId);
                    var deviceRecords = RecordsMapper.daoToModel(allByEntryPersistenceId);
                    return LedgerMapper.daoToModel(ledgerEntryPostgres, deviceRecords);
                });
    }

    @Override
    public void openDeviceLedgerEntry(DeviceId id, LedgerEntry entry) {
        var idS = id.value().toString();
        ledgerRepository.findByDeviceIdAndCloseAtNull(idS)
                .map(e -> {
                    e.closeAt = Timestamp.from(entry.openAt().toClose().value());
                    return e;
                })
                .ifPresent(ledgerRepository::save);
        saveLedger(entry, idS);
    }

    //TODO
    @Override
    public Stream<Device> fetchLatest(Ownership ownership) {
        return Stream.empty();
    }

    //TODO
    @Override
    public Stream<Device> fetch(DeviceQuery query) {
        return Stream.empty();
    }

    private void saveLedger(LedgerEntry entry, String idS) {
        var ledgerEntryPostgres = LedgerMapper.modelToDao(entry, idS);
        var save = ledgerRepository.save(ledgerEntryPostgres);

        var deviceRecordsPostgres = RecordsMapper.modelToDao(entry.content().records(), save);
        recordsRepository.saveAll(deviceRecordsPostgres.collect(Collectors.toSet()));
    }
}

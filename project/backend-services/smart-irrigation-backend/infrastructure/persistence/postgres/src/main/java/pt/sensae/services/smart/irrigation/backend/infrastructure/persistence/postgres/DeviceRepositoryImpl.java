package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.jetbrains.annotations.NotNull;
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
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.LedgerEntryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.LedgerRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

import java.sql.Timestamp;
import java.util.AbstractMap;
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

    @Override
    public Stream<Device> fetchLatest(Ownership ownership) {
        var collect = buildQuery(ownership);
        var latestWithOwnership = ledgerRepository.findLatestWithOwnership(collect);
        return doWorkOnFoundLedgerEntries(latestWithOwnership);
    }

    @Override
    public Stream<Device> fetch(DeviceQuery query) {
        var ownership = buildQuery(query.ownership());
        var latestWithOwnership = ledgerRepository.findOldWithOwnership(ownership, Timestamp.from(query.open().value()), Timestamp.from(query.close().value()));
        return doWorkOnFoundLedgerEntries(latestWithOwnership);
    }

    @NotNull
    private String buildQuery(Ownership ownership) {
        return ownership.value().stream().map(d -> d.value().toString()).collect(Collectors.joining(",", "{", "}"));
    }

    @NotNull
    private Stream<Device> doWorkOnFoundLedgerEntries(Stream<LedgerEntryPostgres> latestWithOwnership) {
        var collect = latestWithOwnership.collect(Collectors.toSet());

        var records = recordsRepository.getAllByEntryPersistenceIdIsIn(collect.stream().map(e -> e.persistenceId).toList())
                .collect(Collectors.groupingBy(name -> name.entryPersistenceId));

        var activeLedgerEntries = collect.stream().map(e -> {
            var deviceRecords = RecordsMapper.daoToModel(records.get(e.persistenceId).stream());
            return new AbstractMap.SimpleEntry<>(e.deviceId, LedgerMapper.daoToModel(e, deviceRecords));
        }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        return deviceRepository.getAllByDeviceIdIsIn(collect.stream().map(e -> e.deviceId).toList())
                .map(d -> DeviceMapper.daoToModel(d, activeLedgerEntries.get(d.deviceId)));
    }

    private void saveLedger(LedgerEntry entry, String idS) {
        var ledgerEntryPostgres = LedgerMapper.modelToDao(entry, idS);
        var save = ledgerRepository.save(ledgerEntryPostgres);

        var deviceRecordsPostgres = RecordsMapper.modelToDao(entry.content().records(), save);
        recordsRepository.saveAll(deviceRecordsPostgres.collect(Collectors.toSet()));
    }
}

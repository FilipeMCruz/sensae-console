package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceRecords;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query.DeviceQuery;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.device.DeviceMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.device.LedgerMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.device.RecordsMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device.LedgerEntryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.device.DeviceRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.device.LedgerRepositoryPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.device.RecordsRepositoryPostgres;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

        var ledgerEntry = device.ledger()
                .entries()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotValidException("A device must be started with a ledger entry"));
        saveLedger(ledgerEntry, saved.deviceId);

        return device;
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Optional<LedgerEntry> fetchDeviceActiveLedgerEntry(DeviceId id) {
        return ledgerRepository.findByDeviceIdAndCloseAtNull(id.value().toString())
                .map(ledgerEntryPostgres -> {
                    var allByEntryPersistenceId = recordsRepository.getAllByEntryPersistenceId(ledgerEntryPostgres.persistenceId);
                    var deviceRecords = RecordsMapper.daoToModel(allByEntryPersistenceId);
                    return LedgerMapper.daoToModel(ledgerEntryPostgres, deviceRecords);
                });
    }

    @Override
    @Transactional("transactionManagerPostgres")
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
    @Transactional("transactionManagerPostgres")
    public Stream<Device> fetchLatest(Ownership ownership) {
        if (ownership.isSystem()) {
            var latest = ledgerRepository.findLatest();
            return doWorkOnFoundLedgerEntries(latest);
        } else {
            var collect = buildQuery(ownership);
            var latestWithOwnership = ledgerRepository.findLatestWithOwnership(collect);
            return doWorkOnFoundLedgerEntries(latestWithOwnership);
        }
    }

    @Override
    @Transactional("transactionManagerPostgres")
    public Stream<Device> fetch(DeviceQuery query) {
        var ownership = buildQuery(query.ownership());
        var latestWithOwnership = ledgerRepository.findOldWithOwnership(ownership, Timestamp.from(query.open()
                .value()), Timestamp.from(query.close().value()));
        return doWorkOnFoundLedgerEntries(latestWithOwnership);
    }

    private String buildQuery(Ownership ownership) {
        return ownership.value().stream().map(d -> d.value().toString()).collect(Collectors.joining(",", "{", "}"));
    }

    private Stream<Device> doWorkOnFoundLedgerEntries(Stream<LedgerEntryPostgres> latestWithOwnership) {
        var collect = latestWithOwnership.collect(Collectors.toSet());

        var records = recordsRepository.getAllByEntryPersistenceIdIsIn(collect.stream()
                        .map(e -> e.persistenceId)
                        .toList())
                .collect(Collectors.groupingBy(name -> name.entryPersistenceId));

        var map = new HashMap<String, Set<LedgerEntry>>();
        collect.forEach(e -> {
            var deviceRecordsPostgres = records.get(e.persistenceId);
            var deviceRecords = new DeviceRecords(new HashSet<>());
            if (deviceRecordsPostgres != null) {
                deviceRecords = RecordsMapper.daoToModel(records.get(e.persistenceId).stream());
            }
            var ledger = LedgerMapper.daoToModel(e, deviceRecords);
            map.computeIfAbsent(e.deviceId, k -> new HashSet<>()).add(ledger);
        });

        var devices = deviceRepository.getAllByDeviceIdIsIn(collect.stream().map(e -> e.deviceId).distinct().toList())
                .map(d -> DeviceMapper.daoToModel(d, map.get(d.deviceId))).toList();
        return devices.stream();
    }

    private void saveLedger(LedgerEntry entry, String idS) {
        var ledgerEntryPostgres = LedgerMapper.modelToDao(entry, idS);
        var save = ledgerRepository.save(ledgerEntryPostgres);

        var deviceRecordsPostgres = RecordsMapper.modelToDao(entry.content().records(), save);
        recordsRepository.saveAll(deviceRecordsPostgres.collect(Collectors.toSet()));
    }
}

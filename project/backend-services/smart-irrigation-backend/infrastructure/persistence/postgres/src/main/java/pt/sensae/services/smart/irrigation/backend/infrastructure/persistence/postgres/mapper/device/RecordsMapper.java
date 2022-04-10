package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceRecords;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.RecordEntry;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device.DeviceRecordsPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device.LedgerEntryPostgres;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecordsMapper {
    public static Stream<DeviceRecordsPostgres> modelToDao(DeviceRecords records, LedgerEntryPostgres ledgerEntryPostgres) {
        return records.entries().stream().map(r -> {
            var deviceRecordsPostgres = new DeviceRecordsPostgres();
            deviceRecordsPostgres.content = r.content();
            deviceRecordsPostgres.label = r.label();
            deviceRecordsPostgres.entryPersistenceId = ledgerEntryPostgres.persistenceId;
            return deviceRecordsPostgres;
        });
    }

    public static DeviceRecords daoToModel(Stream<DeviceRecordsPostgres> recordEntries) {
        var collect = recordEntries.map(r -> new RecordEntry(r.label, r.content)).collect(Collectors.toSet());
        return new DeviceRecords(collect);
    }
}

package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceType;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.DevicePostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.DeviceRecordsPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.LedgerEntryPostgres;

import java.sql.Timestamp;
import java.util.stream.Collectors;

public class DeviceMapper {

//    public static DevicePostgres a(Device model) {
//        var device = new DevicePostgres(model.id().value().toString(), model.type().equals(DeviceType.SENSOR) ? "sensor" : "valve");
//        device.ledgerEntries = model.ledger().entries().stream().map(e -> {
//            String[] arr = new String[e.ownership().value().size()];
//            arr = e.ownership().value().stream().map(id -> id.value().toString()).toList().toArray(arr);
//            var close = e.closeAt().value() != null ? Timestamp.from(e.closeAt().value()) : null;
//            var entry = new LedgerEntryPostgres(device,
//                    Timestamp.from(e.openAt().value()),
//                    close,
//                    e.content().name().value(),
//                    e.content().coordinates().latitude(),
//                    e.content().coordinates().longitude(),
//                    e.content().coordinates().altitude(), arr);
//            entry.records = e.content().records().entries().stream().map(r -> {
//                var deviceRecordsPostgres = new DeviceRecordsPostgres();
//                deviceRecordsPostgres.content = r.content();
//                deviceRecordsPostgres.label = r.label();
//                deviceRecordsPostgres.entry = entry;
//                return deviceRecordsPostgres;
//            }).collect(Collectors.toSet());
//            return entry;
//        }).collect(Collectors.toSet());
//        return device;
//    }

    public static DevicePostgres modelToDao(Device model) {
        return new DevicePostgres(model.id().value().toString(), model.type().equals(DeviceType.SENSOR) ? "sensor" : "valve");
    }
}

package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceContent;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceName;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceRecords;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.LedgerEntryPostgres;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class LedgerMapper {

    public static LedgerEntryPostgres modelToDao(LedgerEntry ledgerEntry, String deviceId) {
        var close = ledgerEntry.closeAt().value() != null ? Timestamp.from(ledgerEntry.closeAt().value()) : null;
        var altitude = ledgerEntry.content().coordinates().altitude() != null ? ledgerEntry.content().coordinates().altitude().toString() : null;
        return new LedgerEntryPostgres(deviceId,
                Timestamp.from(ledgerEntry.openAt().value()),
                close,
                ledgerEntry.content().name().value(),
                ledgerEntry.content().coordinates().latitude().toString(),
                ledgerEntry.content().coordinates().longitude().toString(),
                altitude,
                ledgerEntry.ownership().value().stream().map(id -> id.value().toString()).collect(Collectors.joining(",", "{", "}"))
        );
    }

    public static LedgerEntry daoToModel(LedgerEntryPostgres dao, DeviceRecords records) {
        var aFloat = dao.altitude != null ? Float.parseFloat(dao.altitude) : null;

        var gpsPoint = new GPSPoint(Float.parseFloat(dao.latitude), Float.parseFloat(dao.longitude), aFloat);
        var deviceContent = new DeviceContent(DeviceName.of(dao.deviceName), records, gpsPoint);

        CloseDate close = dao.closeAt != null ? CloseDate.of(dao.closeAt.getTime()) : CloseDate.empty();

        var collect = Arrays.stream(dao.ownership.substring(1, dao.ownership.length() - 2).split(",")).map(UUID::fromString).map(DomainId::of);
        return new LedgerEntry(deviceContent, OpenDate.of(dao.openAt.getTime()), close, Ownership.of(collect));
    }
}

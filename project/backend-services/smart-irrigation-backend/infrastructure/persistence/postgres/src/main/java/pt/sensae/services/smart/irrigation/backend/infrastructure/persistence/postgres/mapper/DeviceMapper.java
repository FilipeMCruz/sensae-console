package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceType;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.DeviceLedger;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.DevicePostgres;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class DeviceMapper {

    public static DevicePostgres modelToDao(Device model) {
        return new DevicePostgres(model.id().value().toString(), model.type().equals(DeviceType.SENSOR) ? "sensor" : "valve");
    }

    public static Device daoToModel(DevicePostgres dao, Set<LedgerEntry> entry) {
        var deviceType = Objects.equals(dao.deviceType, "sensor") ? DeviceType.SENSOR : DeviceType.VALVE;
        return new Device(DeviceId.of(UUID.fromString(dao.deviceId)), deviceType, new DeviceLedger(entry));
    }
}

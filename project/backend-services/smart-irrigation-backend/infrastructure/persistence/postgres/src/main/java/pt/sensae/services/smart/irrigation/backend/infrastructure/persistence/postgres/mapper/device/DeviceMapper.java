package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.device;

import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceType;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.DeviceLedger;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.Set;
import java.util.UUID;

public class DeviceMapper {

    public static DevicePostgres modelToDao(Device model) {
        var type = switch (model.type()) {
            case PARK_SENSOR -> "park_sensor";
            case VALVE -> "valve";
            case STOVE_SENSOR -> "stove_sensor";
        };
        return new DevicePostgres(model.id().value().toString(), type);
    }

    public static Device daoToModel(DevicePostgres dao, Set<LedgerEntry> entry) {
        var deviceType = switch (dao.deviceType) {
            case "park_sensor" -> DeviceType.PARK_SENSOR;
            case "valve" -> DeviceType.VALVE;
            case "stove_sensor" -> DeviceType.STOVE_SENSOR;
            default -> throw new NotValidException("No Valid data packet found");
        };
        return new Device(DeviceId.of(UUID.fromString(dao.deviceId)), deviceType, new DeviceLedger(entry));
    }
}

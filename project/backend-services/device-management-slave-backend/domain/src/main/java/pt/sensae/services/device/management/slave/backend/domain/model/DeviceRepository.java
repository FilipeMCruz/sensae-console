package pt.sensae.services.device.management.slave.backend.domain.model;

import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceRecords;

import java.util.Optional;

public interface DeviceRepository {

    Optional<DeviceRecords> findByDeviceId(DeviceId id);

    DeviceRecords add(DeviceRecords domain);
}

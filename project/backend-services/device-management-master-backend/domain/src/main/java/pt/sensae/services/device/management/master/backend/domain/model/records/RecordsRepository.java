package pt.sensae.services.device.management.master.backend.domain.model.records;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.stream.Stream;

public interface RecordsRepository {

    DeviceRecords save(DeviceRecords records);

    Stream<DeviceRecords> findAll();

    DeviceId delete(DeviceId id);

}

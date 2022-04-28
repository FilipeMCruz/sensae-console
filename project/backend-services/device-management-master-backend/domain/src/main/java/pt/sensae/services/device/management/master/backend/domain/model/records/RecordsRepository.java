package pt.sensae.services.device.management.master.backend.domain.model.records;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.stream.Stream;

public interface RecordsRepository {

    DeviceInformation save(DeviceInformation records);

    Stream<DeviceInformation> findAll();

    DeviceId delete(DeviceId id);

}

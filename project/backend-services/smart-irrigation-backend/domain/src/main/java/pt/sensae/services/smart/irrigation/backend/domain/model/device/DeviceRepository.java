package pt.sensae.services.smart.irrigation.backend.domain.model.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.device.query.DeviceQuery;

import java.util.stream.Stream;

public interface DeviceRepository {

    Device update(Device device);

    Stream<Device> fetchLatest(Ownership ownership);

    Stream<Device> fetch(DeviceQuery query);
}

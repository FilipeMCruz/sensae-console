package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;

import java.util.stream.Stream;

public interface DataRepository {

    void store(Data data);

    Stream<Data> fetch(DataQuery query);

    Stream<Data> fetchLatest(Stream<DeviceId> deviceIds);
}

package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;

import java.util.stream.Stream;

public interface DataRepository {

    Data store(Data data);

    Stream<Data> fetch(Stream<DataQuery> query);

    Stream<Data> fetchLatest(Stream<DeviceId> deviceIds);
}

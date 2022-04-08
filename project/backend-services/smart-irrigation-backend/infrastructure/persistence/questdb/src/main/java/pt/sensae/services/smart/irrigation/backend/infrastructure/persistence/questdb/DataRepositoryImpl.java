package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb;

import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.mapper.DataMapperImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository.DataRepositoryQuestDB;

import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DataRepositoryImpl implements DataRepository {

    private final DataRepositoryQuestDB repository;

    public DataRepositoryImpl(DataRepositoryQuestDB repository) {
        this.repository = repository;
    }

    @Override
    public void store(Data data) {
        var dataQuestDB = DataMapperImpl.toDao(data);
        this.repository.insert(dataQuestDB.dataId,
                dataQuestDB.deviceId,
                dataQuestDB.deviceType,
                dataQuestDB.reportedAt,
                dataQuestDB.temperature,
                dataQuestDB.humidity,
                dataQuestDB.soilMoisture,
                dataQuestDB.illuminance);
    }

    @Override
    public Stream<Data> fetch(DataQuery query) {
        var devices = inConcat(query.deviceId().map(d -> d.value().toString()));
        var open = Timestamp.from(query.open().value()).toString();
        var close = Timestamp.from(query.close().value()).toString();
        return repository.fetch(devices, open, close)
                .map(DataMapperImpl::toModel);
    }

    @Override
    public Stream<Data> fetchLatest(Stream<DeviceId> deviceIds) {
        var devices = inConcat(deviceIds.map(d -> d.value().toString()));
        return repository.fetchLatest(devices).map(DataMapperImpl::toModel);
    }

    private String inConcat(Stream<String> values) {
        return values.map(value -> "'" + value + "'")
                .collect(Collectors.joining(",", "(", ")"));
    }
}

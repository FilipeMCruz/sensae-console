package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.mapper.DataMapperImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.model.DataQuestDB;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository.DataRepositoryQuestDB;

import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DataRepositoryImpl implements DataRepository {

    private final DataRepositoryQuestDB repository;

    private final JdbcTemplate jdbcTemplate;

    public DataRepositoryImpl(DataRepositoryQuestDB repository, @Qualifier("questdb") JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
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
                dataQuestDB.illuminance,
                dataQuestDB.valveStatus);
    }

    @Override
    public Stream<Data> fetch(DataQuery query) {
        if (query.deviceId().isEmpty()) {
            return Stream.empty();
        }
        var devices = inConcat(query.deviceId().stream().map(d -> d.value().toString()));
        var open = Timestamp.from(query.open().value()).toString();
        var close = Timestamp.from(query.close().value()).toString();
        return fetch(devices, open, close)
                .map(DataMapperImpl::toModel);
    }

    @Override
    public Stream<Data> fetchLatest(Stream<DeviceId> deviceIds) {
        var devices = inConcat(deviceIds.map(d -> d.value().toString()));
        return fetchLatest(devices).map(DataMapperImpl::toModel);
    }

    private String inConcat(Stream<String> values) {
        return values.map(value -> "'" + value + "'")
                .collect(Collectors.joining(",", "(", ")"));
    }

    //TODO: "in" clause has a bug in Questdb, for now better use this
    // Values are sanitized so it is not a security issue
    private Stream<DataQuestDB> fetchLatest(String deviceIdArray) {
        var query = String.format("SELECT * FROM smart_irrigation_data LATEST BY device_id WHERE device_id IN %s", deviceIdArray);
        return jdbcTemplate.query(query, (resultSet, i) -> DataMapperImpl.toSensorData(resultSet))
                .stream();
    }

    //TODO: "in" clause has a bug in Questdb, for now better use this
    // Values are sanitized so it is not a security issue
    private Stream<DataQuestDB> fetch(String deviceIdArray, String open, String close) {
        var query = String.format("SELECT * FROM smart_irrigation_data WHERE device_id IN %s AND reported_at BETWEEN '%s' AND '%s'", deviceIdArray, open, close);
        return jdbcTemplate.query(query, (resultSet, i) -> DataMapperImpl.toSensorData(resultSet))
                .stream();
    }
}

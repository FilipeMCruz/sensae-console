package pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.mapper.ProcessedSensorDataMapperImpl;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.repository.ProcessedSensorDataRepositoryJDBC;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sensae.services.fleet.management.backend.domain.SensorDataRepository;
import pt.sensae.services.fleet.management.backend.domain.model.domain.DomainId;
import pt.sensae.services.fleet.management.backend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class SensorDataRepositoryImpl implements SensorDataRepository {

    private final ProcessedSensorDataRepositoryJDBC repository;

    private final ProcessedSensorDataMapperImpl mapper;

    private final JdbcTemplate jdbcTemplate;

    public SensorDataRepositoryImpl(ProcessedSensorDataRepositoryJDBC repository,
                                    ProcessedSensorDataMapperImpl mapper,
                                    JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO change this to a bulk insert with batchUpdate https://www.baeldung.com/spring-jdbc-jdbctemplate
    @Override
    public void insert(SensorDataDTO dao) {
        mapper.dtoToDao(dao).forEach(data ->
                this.repository.insert(data.dataId,
                        data.deviceName,
                        data.deviceId,
                        data.gpsData,
                        data.motion,
                        data.reportedAt,
                        data.domainId));
    }

    //TODO: "in" clause has a bug in Questdb, for now better use this
    // Values are sanitized so it is not a security issue
    @Override
    public Stream<SensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters, Stream<DomainId> domains) {
        var domainValues = inConcat(domains.map(d -> d.value().toString()));
        var deviceValues = inConcat(filters.devices.stream().map(UUID::toString));
        var query = String.format("SELECT * FROM data WHERE device_id IN %s AND domain IN %s AND ts BETWEEN '%s' AND '%s';", deviceValues, domainValues, filters.startTime.toString(), filters.endTime.toString());

        return jdbcTemplate.query(query, (resultSet, i) -> mapper.toSensorData(resultSet))
                .stream()
                .filter(distinctByKey(d -> d.dataId))   //TODO: maybe we can remove this by adding an equals to ProcessedSensorDataDTO and using distinct()
                .map(mapper::daoToDto);
    }

    //TODO: "in" clause has a bug in Questdb, for now better use this
    // Values are sanitized so it is not a security issue
    @Override
    public Stream<SensorDataDTO> lastDataOfEachDevice(Stream<DomainId> domains) {
        var values = inConcat(domains.map(d -> d.value().toString()));
        var query = String.format("SELECT * FROM data LATEST BY device_id WHERE domain IN %s;", values);

        return jdbcTemplate.query(query, (resultSet, i) -> mapper.toSensorData(resultSet))
                .stream()
                .filter(distinctByKey(d -> d.dataId))    //TODO: maybe we can remove this by adding an equals to ProcessedSensorDataDTO and using distinct()
                .map(mapper::daoToDto);
    }

    @Override
    public Stream<SensorDataDTO> queryPastData(SensorDataDTO dao, Integer timeSpanMinutes) {
        var data = mapper.dtoToSingleDao(dao);

        return repository.latestDeviceDataInTime(data.deviceId, data.reportedAt.toString(), timeSpanMinutes)
                .stream()
                .map(mapper::daoToDto);
    }

    private String inConcat(Stream<String> values) {
        return values.map(value -> "'" + value + "'")
                .collect(Collectors.joining(",", "(", ")"));
    }

    private static Predicate<ProcessedSensorDataDAOImpl> distinctByKey(Function<ProcessedSensorDataDAOImpl, String> keyExtractor) {
        var seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}

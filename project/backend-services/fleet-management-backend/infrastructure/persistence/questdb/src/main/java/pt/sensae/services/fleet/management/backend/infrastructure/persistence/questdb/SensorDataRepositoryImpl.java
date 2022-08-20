package pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb;

import io.questdb.client.Sender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.mapper.ProcessedSensorDataMapperImpl;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;
import pt.sensae.services.fleet.management.backend.infrastructure.persistence.questdb.repository.ILPSenderPool;
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
    private final ILPSenderPool senderPool;

    public SensorDataRepositoryImpl(ProcessedSensorDataRepositoryJDBC repository,
                                    ProcessedSensorDataMapperImpl mapper,
                                    JdbcTemplate jdbcTemplate,
                                    ILPSenderPool senderPool) {
        this.repository = repository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
        this.senderPool = senderPool;
    }

    @Override
    public void insert(SensorDataDTO dao) {
        Sender sender = senderPool.getSender();
        mapper.dtoToDao(dao).forEach(data ->
                sender.table("data")
                        .symbol("data_id", data.dataId)
                        .symbol("device_id", data.deviceId)
                        .symbol("device_name", data.deviceName)
                        .symbol("gps_data", data.gpsData)
                        .symbol("motion", data.motion)
                        .symbol("domain", data.domainId)
                        .atNow()
        );
        senderPool.returnSender(sender);
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

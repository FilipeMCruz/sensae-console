package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.mapper.ProcessedSensorDataMapperImpl;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.repository.ProcessedSensorDataRepositoryJDBC;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProcessedSensorDataRepositoryImpl implements ProcessedSensorDataRepository {

    private final ProcessedSensorDataRepositoryJDBC repository;

    private final ProcessedSensorDataMapperImpl mapper;

    private final JdbcTemplate jdbcTemplate;

    public ProcessedSensorDataRepositoryImpl(ProcessedSensorDataRepositoryJDBC repository, ProcessedSensorDataMapperImpl mapper, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(ProcessedSensorDataWithRecordsDTO dao) {
        var data = mapper.dtoToDao(dao);
        this.repository.insert(data.dataId, data.deviceName, data.deviceId, data.gpsData, data.motion, data.reportedAt);
    }

    //TODO: "in" clause has a bug in Questdb, for now better use this
    // Values are sanitized so it is not a security issue
    @Override
    public List<ProcessedSensorDataWithRecordsDTO> queryMultipleDevices(GPSSensorDataFilter filters) {
        String inParams = filters.devices.stream().map(device -> "'" + device + "'").collect(Collectors.joining(","));
        var query = String.format("SELECT * FROM data WHERE device_id IN (%s) AND reported_at BETWEEN '%s' AND '%s';", inParams, filters.startTime.toString(), filters.endTime.toString());
        var data = jdbcTemplate.query(query,
                (resultSet, i) -> mapper.toSensorData(resultSet));
        return data.stream().map(mapper::daoToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProcessedSensorDataWithRecordsDTO> lastDataOfEachDevice() {
        var data = repository.latestDataOfEachDevice();
        return data.stream().map(mapper::daoToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProcessedSensorDataWithRecordsDTO> queryPastData(ProcessedSensorDataWithRecordsDTO dao, Integer timeSpanMinutes) {
        var data = mapper.dtoToDao(dao);
        return repository.latestDeviceDataInTime(data.deviceId, data.reportedAt, timeSpanMinutes)
                .stream()
                .map(mapper::daoToDto)
                .collect(Collectors.toList());
    }
}

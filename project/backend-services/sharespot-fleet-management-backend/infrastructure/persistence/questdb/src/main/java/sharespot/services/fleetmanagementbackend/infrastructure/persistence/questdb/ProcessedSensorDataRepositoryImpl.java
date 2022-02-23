package sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.fleetmanagementbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.fleetmanagementbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.mapper.ProcessedSensorDataMapperImpl;
import sharespot.services.fleetmanagementbackend.infrastructure.persistence.questdb.repository.ProcessedSensorDataRepositoryJDBC;

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
    public void insert(ProcessedSensorDataDTO dao) {
        var data = mapper.dtoToDao(dao);
        this.repository.insert(data.dataId, data.deviceName, data.deviceId, data.gpsData, data.motion, data.reportedAt);
    }

    //TODO: "in" clause has a bug in Questdb, for now better use this
    // Values are sanitized so it is not a security issue
    @Override
    public List<ProcessedSensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters) {
        String inParams = filters.devices.stream().map(device -> "'" + device + "'").collect(Collectors.joining(","));
        var query = String.format("SELECT * FROM data WHERE device_id IN (%s) AND ts BETWEEN '%s' AND '%s';", inParams, filters.startTime.toString(), filters.endTime.toString());
        var data = jdbcTemplate.query(query,
                (resultSet, i) -> mapper.toSensorData(resultSet));
        return data.stream().map(mapper::daoToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProcessedSensorDataDTO> lastDataOfEachDevice() {
        var data = repository.latestDataOfEachDevice();
        return data.stream().map(mapper::daoToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProcessedSensorDataDTO> queryPastData(ProcessedSensorDataDTO dao, Integer timeSpanMinutes) {
        var data = mapper.dtoToDao(dao);
        return repository.latestDeviceDataInTime(data.deviceId, data.reportedAt.toString(), timeSpanMinutes)
                .stream()
                .map(mapper::daoToDto)
                .collect(Collectors.toList());
    }
}

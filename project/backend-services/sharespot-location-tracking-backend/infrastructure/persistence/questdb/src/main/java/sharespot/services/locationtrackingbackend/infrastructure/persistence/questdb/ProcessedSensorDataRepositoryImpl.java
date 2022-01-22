package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb;

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

    public ProcessedSensorDataRepositoryImpl(ProcessedSensorDataRepositoryJDBC repository, ProcessedSensorDataMapperImpl mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void insert(ProcessedSensorDataWithRecordsDTO dao) {
        var data = mapper.dtoToDao(dao);
        this.repository.insert(data.dataId, data.deviceName, data.deviceId, data.gpsData, data.reportedAt);
    }

    @Override
    public List<ProcessedSensorDataWithRecordsDTO> queryDevice2(GPSSensorDataFilter filters) {
        var data = repository.queryByDevice(filters.device, filters.startTime.toString(), filters.endTime.toString());
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

package sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb;

import org.springframework.stereotype.Repository;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataQuery;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.mapper.ProcessedSensorDataMapperImpl;
import sharespot.services.locationtrackingbackend.infrastructure.persistence.questdb.repository.ProcessedSensorDataRepositoryJDBC;

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
        this.repository.insert(data.dataId, data.deviceName, data.deviceId, data.gpsData, data.reportedAt, data.ts);
    }

    @Override
    public GPSSensorDataHistory queryDevice(GPSSensorDataQuery filters) {
        var data = repository.queryByDevice(filters.device, filters.startTime, filters.endTime);
        return mapper.daoToModel(filters, data);
    }
}

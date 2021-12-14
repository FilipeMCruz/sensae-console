package sharespot.services.fastdatastore.infrastructure.persistence.questdb;

import org.springframework.stereotype.Repository;
import sharespot.services.fastdatastore.application.ProcessedSensorDataDAO;
import sharespot.services.fastdatastore.application.ProcessedSensorDataRepository;
import sharespot.services.fastdatastore.infrastructure.persistence.questdb.model.ProcessedSensorDataDAOImpl;
import sharespot.services.fastdatastore.infrastructure.persistence.questdb.repository.ProcessedSensorDataRepositoryJDBC;

@Repository
public class ProcessedSensorDataRepositoryImpl implements ProcessedSensorDataRepository {

    private final ProcessedSensorDataRepositoryJDBC repository;

    public ProcessedSensorDataRepositoryImpl(ProcessedSensorDataRepositoryJDBC repository) {
        this.repository = repository;
    }

    @Override
    public void insert(ProcessedSensorDataDAO dao) {
        var data = (ProcessedSensorDataDAOImpl) dao;
        this.repository.insert(data.dataId, data.deviceId, data.gpsData, data.reportedAt, data.ts);
    }
}

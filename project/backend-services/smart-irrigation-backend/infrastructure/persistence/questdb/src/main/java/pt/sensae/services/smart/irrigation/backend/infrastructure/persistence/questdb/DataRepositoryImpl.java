package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb;

import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.mapper.DataMapperImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository.DataRepositoryQuestDB;

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
    public Stream<Data> fetch(Stream<DataQuery> query) {
        return Stream.empty();
    }

    @Override
    public Stream<Data> fetchLatest(Stream<DeviceId> deviceIds) {
        return Stream.empty();
    }
}

package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.mapper.DataMapperImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.repository.DataRepositoryQuestDB;

import java.util.stream.Stream;

public class DataRepositoryImpl implements DataRepository {

    private final DataRepositoryQuestDB repository;

    public DataRepositoryImpl(DataRepositoryQuestDB repository) {
        this.repository = repository;
    }

    @Override
    public Data store(Data data) {
        var dataQuestDB = DataMapperImpl.toDao(data);
        var save = this.repository.save(dataQuestDB);
        return DataMapperImpl.toModel(save);
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

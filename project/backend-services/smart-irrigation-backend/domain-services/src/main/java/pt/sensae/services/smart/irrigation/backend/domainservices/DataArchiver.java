package pt.sensae.services.smart.irrigation.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

@Service
public class DataArchiver {

    private final DataRepository repository;

    private final DataMapper mapper;

    public DataArchiver(DataRepository repository, DataMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(ProcessedSensorDataDTO data) {
        var model = mapper.dtoToModel(data);
        repository.store(model);
    }
}

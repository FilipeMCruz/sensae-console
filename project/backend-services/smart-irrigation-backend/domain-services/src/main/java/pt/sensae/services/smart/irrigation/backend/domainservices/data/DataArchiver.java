package pt.sensae.services.smart.irrigation.backend.domainservices.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domainservices.data.mapper.DataMapper;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

@Service
public class DataArchiver {

    private final DataRepository repository;

    public DataArchiver(DataRepository repository) {
        this.repository = repository;
    }

    public void save(DataUnitDTO data) {
        var model = DataMapper.dtoToModel(data);
        repository.store(model);
    }
}

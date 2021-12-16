package sharespot.services.fastdatastore.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

@Service
public class SensorDataHandlerService {

    private final ProcessedSensorDataMapper mapper;
    private final ProcessedSensorDataRepository repository;

    public SensorDataHandlerService(ProcessedSensorDataMapper mapper,
                                    ProcessedSensorDataRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void publish(ProcessedSensorDataDTO in) {
        var dao = mapper.dtoToDao(in);
        repository.insert(dao);
    }
}

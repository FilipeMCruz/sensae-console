package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.ProcessedSensorDataMapper;

import java.util.Optional;

@Service
public class SensorDataMapper {

    private final SensorDataTransformationsRepository repository;

    private final ProcessedSensorDataMapper mapper;

    public SensorDataMapper(SensorDataTransformationsRepository repository, ProcessedSensorDataMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<SensorDataDTO> inToOut(JsonNode inDto, SensorTypeId typeId) {
        return repository.getById(typeId)
                .flatMap(dt -> mapper.process(dt.getTransform(), inDto));
    }
}

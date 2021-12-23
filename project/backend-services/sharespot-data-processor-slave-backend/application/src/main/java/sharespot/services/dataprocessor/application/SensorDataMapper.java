package sharespot.services.dataprocessor.application;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.ProcessedSensorDataMapper;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.domainservices.DataTransformationCache;

import java.util.Optional;

@Service
public class SensorDataMapper {

    private final DataTransformationCache cache;

    private final ProcessedSensorDataMapper mapper;

    public SensorDataMapper(DataTransformationCache cache, ProcessedSensorDataMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    public Optional<SensorDataDTO> inToOut(JsonNode inDto, SensorTypeId typeId) {
        return cache.findByDeviceId(typeId)
                .flatMap(dt -> mapper.process(dt.getTransform(), inDto));
    }
}

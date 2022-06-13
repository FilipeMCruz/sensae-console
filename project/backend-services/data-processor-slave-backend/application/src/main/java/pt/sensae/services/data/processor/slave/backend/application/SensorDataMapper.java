package pt.sensae.services.data.processor.slave.backend.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.ProcessedSensorDataMapper;
import pt.sensae.services.data.processor.slave.backend.domain.SensorTypeId;
import pt.sensae.services.data.processor.slave.backend.domainservices.DataTransformationCache;

import java.util.Optional;

@Service
public class SensorDataMapper {

    private final DataTransformationCache cache;

    private final ProcessedSensorDataMapper mapper;

    public SensorDataMapper(DataTransformationCache cache, ObjectMapper mapper) {
        this.mapper = new ProcessedSensorDataMapper(mapper);
        this.cache = cache;
    }

    public Optional<SensorDataDTO> inToOut(JsonNode inDto, SensorTypeId typeId) {
        return cache.findById(typeId)
                .flatMap(dt -> mapper.process(dt.getTransform(), inDto));
    }
}

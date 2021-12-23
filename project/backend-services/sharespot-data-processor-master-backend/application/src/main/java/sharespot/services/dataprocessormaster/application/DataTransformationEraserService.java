package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationEraser;

@Service
public class DataTransformationEraserService {

    private final DataTransformationEraser eraser;

    private final DataTransformationMapper mapper;

    private final DataTransformationHandlerService publisher;

    public DataTransformationEraserService(DataTransformationEraser eraser, DataTransformationMapper mapper, DataTransformationHandlerService publisher) {
        this.eraser = eraser;
        this.mapper = mapper;
        this.publisher = publisher;
    }

    public SensorTypeIdDTO erase(SensorTypeIdDTO dto) {
        var deviceId = mapper.dtoToDomain(dto);
        var erased = eraser.erase(deviceId);
        publisher.publishUpdate(erased);
        return mapper.domainToDto(erased);
    }
}

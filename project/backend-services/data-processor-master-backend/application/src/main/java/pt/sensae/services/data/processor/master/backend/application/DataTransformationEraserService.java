package pt.sensae.services.data.processor.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.processor.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.processor.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.processor.master.backend.domainservices.DataTransformationEraser;

@Service
public class DataTransformationEraserService {

    private final DataTransformationEraser eraser;

    private final DataTransformationMapper mapper;

    private final DataTransformationEventHandlerService publisher;

    private final TokenExtractor authHandler;

    public DataTransformationEraserService(DataTransformationEraser eraser,
                                           DataTransformationMapper mapper,
                                           DataTransformationEventHandlerService publisher,
                                           TokenExtractor authHandler) {
        this.eraser = eraser;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
    }

    public SensorTypeIdDTO erase(SensorTypeIdDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_transformations:transformations:delete"))
            throw new UnauthorizedException("No Permissions");

        var deviceId = mapper.dtoToDomain(dto);
        var erased = eraser.erase(deviceId);
        publisher.publishDelete(erased);
        return mapper.domainToDto(erased);
    }
}

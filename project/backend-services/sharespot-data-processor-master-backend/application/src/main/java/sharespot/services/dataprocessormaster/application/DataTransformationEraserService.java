package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.application.auth.AccessTokenDTO;
import sharespot.services.dataprocessormaster.application.auth.TokenExtractor;
import sharespot.services.dataprocessormaster.application.auth.UnauthorizedException;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationEraser;

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

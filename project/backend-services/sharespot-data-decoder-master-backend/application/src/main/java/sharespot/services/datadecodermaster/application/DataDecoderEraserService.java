package sharespot.services.datadecodermaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.application.auth.AccessTokenDTO;
import sharespot.services.datadecodermaster.application.auth.TokenExtractor;
import sharespot.services.datadecodermaster.application.auth.UnauthorizedException;
import sharespot.services.datadecodermaster.domainservices.DataDecoderEraser;

@Service
public class DataDecoderEraserService {

    private final DataDecoderEraser eraser;

    private final DataDecoderMapper mapper;

    private final DataDecoderHandlerService publisher;

    private final TokenExtractor authHandler;

    public DataDecoderEraserService(DataDecoderEraser eraser,
                                    DataDecoderMapper mapper,
                                    DataDecoderHandlerService publisher,
                                    TokenExtractor authHandler) {
        this.eraser = eraser;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
    }

    public SensorTypeIdDTO erase(SensorTypeIdDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_decoders:decoders:write"))
            throw new UnauthorizedException("No Permissions");

        var deviceId = mapper.dtoToDomain(dto);
        var erased = eraser.erase(deviceId);
        publisher.publishUpdate(erased);
        return mapper.domainToDto(erased);
    }
}

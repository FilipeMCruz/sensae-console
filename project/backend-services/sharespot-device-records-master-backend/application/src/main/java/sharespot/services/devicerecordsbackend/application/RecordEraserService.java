package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.auth.AccessTokenDTO;
import sharespot.services.devicerecordsbackend.application.auth.TokenExtractor;
import sharespot.services.devicerecordsbackend.application.auth.UnauthorizedException;
import sharespot.services.devicerecordsbackend.domainservices.RecordEraser;

@Service
public class RecordEraserService {

    private final RecordEraser eraser;

    private final RecordMapper mapper;

    private final RecordEventHandlerService publisher;

    private final TokenExtractor authHandler;

    public RecordEraserService(RecordEraser eraser,
                               RecordMapper mapper,
                               RecordEventHandlerService publisher,
                               TokenExtractor authHandler) {
        this.eraser = eraser;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
    }

    public DeviceDTO erase(DeviceDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_records:records:delete"))
            throw new UnauthorizedException("No Permissions");
        
        var deviceId = mapper.dtoToDomain(dto);
        var erased = eraser.erase(deviceId);
        publisher.publishUpdate(erased);
        return mapper.domainToDto(erased);
    }
}

package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.auth.AccessTokenDTO;
import sharespot.services.devicerecordsbackend.application.auth.TokenExtractor;
import sharespot.services.devicerecordsbackend.application.auth.UnauthorizedException;
import sharespot.services.devicerecordsbackend.domainservices.RecordHoarder;

@Service
public class RecordRegisterService {

    private final RecordHoarder hoarder;

    private final RecordMapper mapper;

    private final RecordEventHandlerService publisher;

    private final TokenExtractor authHandler;

    public RecordRegisterService(RecordHoarder hoarder,
                                 RecordMapper mapper,
                                 RecordEventHandlerService publisher,
                                 TokenExtractor authHandler) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
    }

    public DeviceRecordDTO register(DeviceRecordDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_records:records:write"))
            throw new UnauthorizedException("No Permissions");

        var deviceRecords = mapper.dtoToDomain(dto);
        hoarder.hoard(deviceRecords);
        publisher.publishUpdate(deviceRecords.device().id());
        return dto;
    }
}

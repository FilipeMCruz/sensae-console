package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceIdentityCache;
import pt.sensae.services.device.management.master.backend.application.ownership.DomainId;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationEraser;

import java.util.UUID;

@Service
public class RecordEraserService {

    private final DeviceInformationEraser eraser;

    private final RecordMapper mapper;

    private final DeviceInformationEventHandlerService publisher;

    private final TokenExtractor authHandler;

    private final DeviceIdentityCache ownerChecker;

    public RecordEraserService(DeviceInformationEraser eraser,
                               RecordMapper mapper,
                               DeviceInformationEventHandlerService publisher,
                               TokenExtractor authHandler,
                               DeviceIdentityCache ownerChecker) {
        this.eraser = eraser;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
        this.ownerChecker = ownerChecker;
    }

    public DeviceDTO erase(DeviceDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:delete"))
            throw new UnauthorizedException("No Permissions");

        var deviceId = mapper.dtoToDomain(dto);

        var owns = ownerChecker.owns(extract.domains.stream().map(UUID::fromString).map(DomainId::of)).toList();

        if (owns.contains(deviceId)) {
            var erased = eraser.erase(deviceId);
            publisher.publishDelete(erased);
        }
        return mapper.domainToDto(deviceId);
    }
}

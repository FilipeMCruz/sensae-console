package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceIdentityRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DomainId;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationEraser;

import java.util.UUID;

@Service
public class DeviceInformationEraserService {

    private final DeviceInformationEraser eraser;

    private final DeviceInformationMapper mapper;

    private final DeviceInformationEventHandlerService publisher;

    private final TokenExtractor authHandler;

    private final DeviceIdentityRepository ownerChecker;

    public DeviceInformationEraserService(DeviceInformationEraser eraser,
                                          DeviceInformationMapper mapper,
                                          DeviceInformationEventHandlerService publisher,
                                          TokenExtractor authHandler,
                                          DeviceIdentityRepository ownerChecker) {
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

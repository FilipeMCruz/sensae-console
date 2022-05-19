package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceDomainCheckerService;
import pt.sensae.services.device.management.master.backend.domainservices.RecordHoarder;

@Service
public class RecordRegisterService {

    private final RecordHoarder hoarder;

    private final RecordMapper mapper;

    private final DeviceInformationEventHandlerService publisher;

    private final TokenExtractor authHandler;

    private final DeviceDomainCheckerService ownerChecker;

    public RecordRegisterService(RecordHoarder hoarder,
                                 RecordMapper mapper,
                                 DeviceInformationEventHandlerService publisher,
                                 TokenExtractor authHandler, DeviceDomainCheckerService ownerChecker) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
        this.ownerChecker = ownerChecker;
    }

    public DeviceInformationDTO register(DeviceInformationDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:edit"))
            throw new UnauthorizedException("No Permissions");

        var deviceInformation = mapper.dtoToDomain(dto);
        var id = deviceInformation.device().id();

        if (hoarder.exists(id)) {
            if (!ownerChecker.owns(claims).toList().contains(id)) {
                throw new UnauthorizedException("No Permissions");
            }
        }

        var hoard = hoarder.hoard(deviceInformation);
        publisher.publishUpdate(hoard);
        return dto;
    }
}

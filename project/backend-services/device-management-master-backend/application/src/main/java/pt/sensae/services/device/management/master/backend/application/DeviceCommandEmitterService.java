package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceIdentityRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DomainId;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationCollector;

import java.util.UUID;

@Service
public class DeviceCommandEmitterService {

    private final DeviceInformationCollector collector;

    private final DeviceInformationMapper mapper;

    private final DeviceEventMapper eventMapper;

    private final TokenExtractor authHandler;

    private final DeviceIdentityRepository ownerChecker;

    private final DeviceCommandTestService commandTestService;

    public DeviceCommandEmitterService(DeviceInformationCollector collector,
                                       DeviceInformationMapper mapper,
                                       DeviceEventMapper eventMapper,
                                       TokenExtractor authHandler,
                                       DeviceIdentityRepository deviceIdentityRepository,
                                       DeviceCommandTestService commandTestService) {
        this.collector = collector;
        this.mapper = mapper;
        this.eventMapper = eventMapper;
        this.authHandler = authHandler;
        this.ownerChecker = deviceIdentityRepository;
        this.commandTestService = commandTestService;
    }

    public void command(DeviceCommandDTO command, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:command"))
            throw new UnauthorizedException("No Permissions");

        var deviceCommand = mapper.dtoToDomain(command);

        var owns = ownerChecker.owns(extract.domains.stream().map(UUID::fromString).map(DomainId::of))
                .filter(d -> d.equals(deviceCommand.id()));

        var deviceCommandDTO = collector.collect(owns)
                .filter(info -> info.device().downlink().exists())
                .filter(info -> info.commands()
                        .entries()
                        .stream()
                        .anyMatch(c -> c.id().equals(deviceCommand.commandId())))
                .findAny()
                .map(a -> eventMapper.domainToCommandDto(deviceCommand))
                .orElseThrow(() -> new RuntimeException("Command " + deviceCommand.commandId()
                        .value() + " in device " + deviceCommand.id().value() + " not Found"));

        commandTestService.send(deviceCommandDTO);
    }
}

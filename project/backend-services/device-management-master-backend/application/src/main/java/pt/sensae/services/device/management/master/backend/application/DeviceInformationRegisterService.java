package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.domain.model.LastTimeSeenDeviceRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceIdentityRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DomainId;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationHoarder;

import java.time.Instant;
import java.util.UUID;

@Service
public class DeviceInformationRegisterService {

    private final DeviceInformationHoarder hoarder;

    private final DeviceInformationMapper mapper;

    private final DeviceInformationEventHandlerService publisher;

    private final TokenExtractor authHandler;

    private final DeviceIdentityRepository ownerChecker;

    private final LastTimeSeenDeviceRepository lastTimeSeenDeviceRepository;

    public DeviceInformationRegisterService(DeviceInformationHoarder hoarder,
                                            DeviceInformationMapper mapper,
                                            DeviceInformationEventHandlerService publisher,
                                            TokenExtractor authHandler,
                                            DeviceIdentityRepository ownerChecker,
                                            LastTimeSeenDeviceRepository lastTimeSeenDeviceRepository) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
        this.ownerChecker = ownerChecker;
        this.lastTimeSeenDeviceRepository = lastTimeSeenDeviceRepository;
    }

    public DeviceInformationDTO register(DeviceInformationDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:edit"))
            throw new UnauthorizedException("No Permissions");

        var deviceInformation = mapper.dtoToDomain(dto);
        var id = deviceInformation.device().id();

        if (hoarder.exists(id)) {
            if (!ownerChecker.owns(extract.domains.stream().map(UUID::fromString).map(DomainId::of))
                    .toList()
                    .contains(id)) {
                throw new UnauthorizedException("No Permissions");
            }
        }

        var hoard = hoarder.hoard(deviceInformation);
        publisher.publishUpdate(hoard);

        var lastTimeSeen = lastTimeSeenDeviceRepository.find(hoard.device().id())
                .map(Instant::toEpochMilli)
                .orElse(0L);

        return mapper.domainToDto(hoard, lastTimeSeen);
    }
}

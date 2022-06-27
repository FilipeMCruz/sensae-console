package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.domain.model.LastTimeSeenDeviceRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DeviceIdentityRepository;
import pt.sensae.services.device.management.master.backend.domain.model.identity.DomainId;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationCollector;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class DeviceInformationCollectorService {

    private final DeviceInformationCollector collector;

    private final DeviceInformationMapper mapper;

    private final TokenExtractor authHandler;

    private final DeviceIdentityRepository ownerChecker;

    private final LastTimeSeenDeviceRepository lastTimeSeenDeviceRepository;

    public DeviceInformationCollectorService(DeviceInformationCollector collector,
                                             DeviceInformationMapper mapper,
                                             TokenExtractor authHandler,
                                             DeviceIdentityRepository deviceIdentityCache,
                                             LastTimeSeenDeviceRepository lastTimeSeenDeviceRepository) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
        this.ownerChecker = deviceIdentityCache;
        this.lastTimeSeenDeviceRepository = lastTimeSeenDeviceRepository;
    }

    public Stream<DeviceInformationDTO> catalog(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:read"))
            throw new UnauthorizedException("No Permissions");

        var owns = ownerChecker.owns(extract.domains.stream().map(UUID::fromString).map(DomainId::of));

        return collector.collect(owns)
                .map(dev -> mapper.domainToDto(dev, lastTimeSeenDeviceRepository.find(dev.device().id())
                        .map(Instant::getEpochSecond)
                        .orElse(0L)));
    }
}

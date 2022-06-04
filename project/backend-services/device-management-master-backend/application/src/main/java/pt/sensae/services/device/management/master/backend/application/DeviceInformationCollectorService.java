package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceIdentityCache;
import pt.sensae.services.device.management.master.backend.application.ownership.DomainId;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationCollector;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class DeviceInformationCollectorService {

    private final DeviceInformationCollector collector;

    private final RecordMapper mapper;

    private final TokenExtractor authHandler;

    private final DeviceIdentityCache ownerChecker;

    public DeviceInformationCollectorService(DeviceInformationCollector collector, RecordMapper mapper, TokenExtractor authHandler, DeviceIdentityCache deviceIdentityCache) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
        this.ownerChecker = deviceIdentityCache;
    }

    public Stream<DeviceInformationDTO> catalog(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:read"))
            throw new UnauthorizedException("No Permissions");

        var owns = ownerChecker.owns(extract.domains.stream().map(UUID::fromString).map(DomainId::of)).toList();

        return collector.collect().filter(i -> owns.contains(i.device().id())).map(mapper::domainToDto);
    }
}

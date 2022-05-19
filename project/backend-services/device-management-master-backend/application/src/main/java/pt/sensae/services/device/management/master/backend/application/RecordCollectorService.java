package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceDomainCheckerService;
import pt.sensae.services.device.management.master.backend.domainservices.RecordCollector;

import java.util.stream.Stream;

@Service
public class RecordCollectorService {

    private final RecordCollector collector;

    private final RecordMapper mapper;

    private final TokenExtractor authHandler;

    private final DeviceDomainCheckerService ownerChecker;

    public RecordCollectorService(RecordCollector collector, RecordMapper mapper, TokenExtractor authHandler, DeviceDomainCheckerService deviceDomainCheckerService) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
        this.ownerChecker = deviceDomainCheckerService;
    }

    public Stream<DeviceInformationDTO> catalog(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:read"))
            throw new UnauthorizedException("No Permissions");

        var owns = ownerChecker.owns(claims).toList();

        return collector.collect().filter(i -> owns.contains(i.device().id())).map(mapper::domainToDto);
    }
}

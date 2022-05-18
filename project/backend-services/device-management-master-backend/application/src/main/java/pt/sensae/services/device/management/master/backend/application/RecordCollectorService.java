package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.device.management.master.backend.domainservices.RecordCollector;

import java.util.stream.Stream;

@Service
public class RecordCollectorService {

    private final RecordCollector collector;

    private final RecordMapper mapper;

    private final TokenExtractor authHandler;

    public RecordCollectorService(RecordCollector collector, RecordMapper mapper, TokenExtractor authHandler) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
    }

    public Stream<DeviceInformationDTO> catalog(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_management:device:read"))
            throw new UnauthorizedException("No Permissions");

        return collector.collect().map(mapper::domainToDto);
    }
}

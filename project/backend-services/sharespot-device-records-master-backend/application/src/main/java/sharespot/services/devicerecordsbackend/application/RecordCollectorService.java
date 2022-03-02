package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.auth.AccessTokenDTO;
import sharespot.services.devicerecordsbackend.application.auth.TokenExtractor;
import sharespot.services.devicerecordsbackend.application.auth.UnauthorizedException;
import sharespot.services.devicerecordsbackend.domainservices.RecordCollector;

import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<DeviceRecordDTO> records(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("device_records:records:read"))
            throw new UnauthorizedException("No Permissions");
        return collector.collect()
                .stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toSet());
    }
}

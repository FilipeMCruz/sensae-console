package pt.sensae.services.smart.irrigation.backend.application.services.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorReadingDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.LatestDataCollector;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.model.LatestDataQuery;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceLatestDataCollectorService {

    private final LatestDataCollector collector;

    private final TokenExtractor authHandler;

    private final SensorDataMapper mapper;

    public DeviceLatestDataCollectorService(LatestDataCollector collector, TokenExtractor authHandler, SensorDataMapper mapper) {
        this.collector = collector;
        this.authHandler = authHandler;
        this.mapper = mapper;
    }

    public Stream<SensorReadingDTO> fetch(Stream<String> irrigationZoneIds, Stream<String> deviceIds, AccessTokenDTO claims) {
        var domainFilter = Ownership.of(getDomainFilter(claims));

        var deviceFilter = deviceIds.map(UUID::fromString).map(DeviceId::new).collect(Collectors.toSet());

        var irrigationZoneFilter = irrigationZoneIds.map(UUID::fromString).map(IrrigationZoneId::new).collect(Collectors.toSet());

        var query = new LatestDataQuery(irrigationZoneFilter, deviceFilter, domainFilter);

        return collector.fetch(query).map(mapper::toDto);
    }

    private Stream<DomainId> getDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:latest_data:read"))
            throw new UnauthorizedException("No Permissions");

        return extract.domains.stream().map(UUID::fromString).map(DomainId::new);
    }
}

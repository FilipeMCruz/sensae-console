package pt.sensae.services.smart.irrigation.backend.application.services.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.SensorDataHistoryMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.DeviceHistoryDataCollector;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.model.HistoryQuery;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceHistoryDataCollectorService {

    private final DeviceHistoryDataCollector collector;

    private final TokenExtractor authHandler;

    private final SensorDataHistoryMapper mapper;

    public DeviceHistoryDataCollectorService(DeviceHistoryDataCollector collector, TokenExtractor authHandler, SensorDataHistoryMapper mapper) {
        this.collector = collector;
        this.authHandler = authHandler;
        this.mapper = mapper;
    }

    public Stream<SensorDataHistoryDTO> fetch(Stream<String> irrigationZoneIds, Stream<String> deviceIds, Instant openDate, Instant closeDate, AccessTokenDTO claims) {
        var domainFilter = getDomainFilter(claims).collect(Collectors.toSet());

        var deviceFilter = deviceIds.map(UUID::fromString).map(DeviceId::new).collect(Collectors.toSet());
        var irrigationZoneFilter = irrigationZoneIds.map(UUID::fromString).map(IrrigationZoneId::new).collect(Collectors.toSet());

        var historyQuery = new HistoryQuery(irrigationZoneFilter, deviceFilter, domainFilter, new OpenDate(openDate), new CloseDate(closeDate));

        return collector.fetch(historyQuery).map(mapper::toDto);
    }

    private Stream<DomainId> getDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:past_data:read"))
            throw new UnauthorizedException("No Permissions");

        return extract.domains.stream().map(UUID::fromString).map(DomainId::new);
    }
}

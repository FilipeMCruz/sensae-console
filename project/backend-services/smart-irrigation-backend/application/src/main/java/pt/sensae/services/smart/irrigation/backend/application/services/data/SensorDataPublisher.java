package pt.sensae.services.smart.irrigation.backend.application.services.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.LiveDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilter;
import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilterDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorReadingDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.IrrigationZoneCache;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class SensorDataPublisher {

    private FluxSink<DataUnitDTO> dataStream;

    private ConnectableFlux<DataUnitDTO> dataPublisher;

    private final TokenExtractor authHandler;

    private final SensorDataMapper mapper;

    private final IrrigationZoneCache irrigationZoneCache;

    private final LiveDataMapper filterMapper;

    public SensorDataPublisher(TokenExtractor authHandler, SensorDataMapper mapper, IrrigationZoneCache irrigationZoneCache, LiveDataMapper filterMapper) {
        this.authHandler = authHandler;
        this.mapper = mapper;
        this.irrigationZoneCache = irrigationZoneCache;
        this.filterMapper = filterMapper;
    }

    @PostConstruct
    public void init() {
        Flux<DataUnitDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<SensorReadingDTO> getFilteredPublisher(LiveDataFilterDTO filter, AccessTokenDTO claims) {
        var liveDataFilter = filterMapper.dtoToModel(filter);
        return dataPublisher
                .filter(withFilter(liveDataFilter, claims))
                .map(mapper::toDto);
    }

    public void publish(DataUnitDTO data) {
        dataStream.next(data);
    }

    private Predicate<DataUnitDTO> withFilter(LiveDataFilter filter, AccessTokenDTO claims) {
        return getDeviceDomainFilter(claims)
                .and(withContent(filter.content()))
                .and(insideGardeningArea(filter.irrigationZones()))
                .and(withDeviceId(filter.devices()));
    }

    private Predicate<DataUnitDTO> insideGardeningArea(Set<IrrigationZoneId> irrigationZoneIds) {
        if (irrigationZoneIds.isEmpty()) {
            return data -> true;
        }
        return data -> this.irrigationZoneCache.fetchByIds(irrigationZoneIds.stream())
                .anyMatch(g -> g.area().contains(GPSPoint.from(data.getSensorData().gps)));
    }

    private Predicate<DataUnitDTO> withContent(String content) {
        if (content.isEmpty()) {
            return data -> true;
        }
        return data -> data.device.records
                .stream()
                .anyMatch(e -> e.content.contains(content));
    }

    private Predicate<DataUnitDTO> withDeviceId(Set<DeviceId> ids) {
        if (ids.isEmpty()) {
            return data -> true;
        }
        return data -> ids.stream().anyMatch(id -> Objects.equals(id.value(), data.device.id));
    }

    private Predicate<DataUnitDTO> getDeviceDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:live_data:read"))
            throw new UnauthorizedException("No Permissions");

        return withDomain(extract.domains.stream().map(UUID::fromString).toList());
    }

    private Predicate<DataUnitDTO> withDomain(List<UUID> domainIds) {
        return s -> s.device.domains.stream().anyMatch(domainIds::contains);
    }
}

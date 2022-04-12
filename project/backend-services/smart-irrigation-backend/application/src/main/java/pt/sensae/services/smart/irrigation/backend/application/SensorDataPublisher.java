package pt.sensae.services.smart.irrigation.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.mapper.LiveDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.LiveDataFilter;
import pt.sensae.services.smart.irrigation.backend.application.model.LiveDataFilterDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.GardeningAreaCache;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
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

    private FluxSink<ProcessedSensorDataDTO> dataStream;

    private ConnectableFlux<ProcessedSensorDataDTO> dataPublisher;

    private final TokenExtractor authHandler;

    private final SensorDataMapper mapper;

    private final GardeningAreaCache gardenCache;

    private final LiveDataMapper filterMapper;

    public SensorDataPublisher(TokenExtractor authHandler, SensorDataMapper mapper, GardeningAreaCache gardenCache, LiveDataMapper filterMapper) {
        this.authHandler = authHandler;
        this.mapper = mapper;
        this.gardenCache = gardenCache;
        this.filterMapper = filterMapper;
    }

    @PostConstruct
    public void init() {
        Flux<ProcessedSensorDataDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<SensorDataDTO> getFilteredPublisher(LiveDataFilterDTO filter, AccessTokenDTO claims) {
        var liveDataFilter = filterMapper.dtoToModel(filter);
        return dataPublisher
                .filter(withFilter(liveDataFilter, claims))
                .map(mapper::toDto);
    }

    public void publish(ProcessedSensorDataDTO data) {
        dataStream.next(data);
    }

    private Predicate<ProcessedSensorDataDTO> withFilter(LiveDataFilter filter, AccessTokenDTO claims) {
        return getDeviceDomainFilter(claims)
                .and(withContent(filter.content()))
                .and(insideGardeningArea(filter.gardens()))
                .and(withDeviceId(filter.devices()));
    }

    private Predicate<ProcessedSensorDataDTO> insideGardeningArea(Set<GardeningAreaId> gardenIds) {
        if (gardenIds.isEmpty()) {
            return data -> true;
        }
        return data -> this.gardenCache.fetchByIds(gardenIds.stream())
                .anyMatch(g -> g.area().contains(GPSPoint.from(data.data.gps)));
    }

    private Predicate<ProcessedSensorDataDTO> withContent(String content) {
        if (content.isEmpty()) {
            return data -> true;
        }
        return data -> data.device.records.entry
                .stream()
                .anyMatch(e -> e.content.contains(content));
    }

    private Predicate<ProcessedSensorDataDTO> withDeviceId(Set<DeviceId> ids) {
        if (ids.isEmpty()) {
            return data -> true;
        }
        return data -> ids.stream().anyMatch(id -> Objects.equals(id.value(), data.device.id));
    }

    private Predicate<ProcessedSensorDataDTO> getDeviceDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        //TODO: Add new permissions
//        if (!extract.permissions.contains("smart_irrigation:live_data:read"))
//            throw new UnauthorizedException("No Permissions");

        return withDomain(extract.domains.stream().map(UUID::fromString).toList());
    }

    private Predicate<ProcessedSensorDataDTO> withDomain(List<UUID> domainIds) {
        return s -> s.device.domains.readWrite.stream().anyMatch(domainIds::contains) ||
                s.device.domains.read.stream().anyMatch(domainIds::contains);
    }
}

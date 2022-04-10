package pt.sensae.services.smart.irrigation.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.mapper.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.GardeningAreaCache;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class SensorDataPublisher {

    private FluxSink<ProcessedSensorDataDTO> dataStream;

    private ConnectableFlux<ProcessedSensorDataDTO> dataPublisher;

    private final TokenExtractor authHandler;

    private final SensorDataMapper mapper;

    private final GardeningAreaCache gardenCache;

    public SensorDataPublisher(TokenExtractor authHandler, SensorDataMapper mapper, GardeningAreaCache gardenCache) {
        this.authHandler = authHandler;
        this.mapper = mapper;
        this.gardenCache = gardenCache;
    }

    @PostConstruct
    public void init() {
        Flux<ProcessedSensorDataDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<SensorDataDTO> getGeneralPublisher(AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .map(mapper::toDto);
    }

    public Flux<SensorDataDTO> getGardenFilteredPublisher(Stream<String> gardenIds, AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .filter(insideGardeningArea(gardenIds))
                .map(mapper::toDto);
    }

    public Flux<SensorDataDTO> getContentFilteredPublisher(String content, AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .filter(withContent(content))
                .map(mapper::toDto);
    }

    public Flux<SensorDataDTO> getDeviceFilteredPublisher(Stream<String> ids, AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .filter(withDeviceId(ids))
                .map(mapper::toDto);
    }

    public void publish(ProcessedSensorDataDTO data) {
        dataStream.next(data);
    }

    private Predicate<ProcessedSensorDataDTO> insideGardeningArea(Stream<String> gardenIds) {
        return data -> this.gardenCache.fetchByIds(gardenIds.map(UUID::fromString).map(GardeningAreaId::of))
                .anyMatch(g -> g.area().contains(GPSPoint.from(data.data.gps)));
    }

    private Predicate<ProcessedSensorDataDTO> withContent(String content) {
        return data -> data.device.records.entry
                .stream()
                .anyMatch(e -> e.content.contains(content));
    }

    private Predicate<ProcessedSensorDataDTO> withDeviceId(Stream<String> ids) {
        return data -> ids.anyMatch(id -> Objects.equals(id, data.device.id.toString()));
    }

    private Predicate<? super ProcessedSensorDataDTO> getDeviceDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        //TODO: Add new permissions
//        if (!extract.permissions.contains("smart_irrigation:live_data:read"))
//            throw new UnauthorizedException("No Permissions");

        return withDomain(extract.domains.stream().map(UUID::fromString).toList());
    }

    private Predicate<? super ProcessedSensorDataDTO> withDomain(List<UUID> domainIds) {
        return s -> s.device.domains.readWrite.stream().anyMatch(domainIds::contains) ||
                s.device.domains.read.stream().anyMatch(domainIds::contains);
    }
}

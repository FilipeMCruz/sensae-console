package sharespot.services.fleetmanagementbackend.application;

import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.fleetmanagementbackend.application.auth.AccessTokenDTO;
import sharespot.services.fleetmanagementbackend.application.auth.TokenExtractor;
import sharespot.services.fleetmanagementbackend.application.auth.UnauthorizedException;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Component
public class GPSDataPublisher {

    private FluxSink<ProcessedSensorDataDTO> dataStream;

    private ConnectableFlux<ProcessedSensorDataDTO> dataPublisher;

    private final TokenExtractor authHandler;

    public GPSDataPublisher(TokenExtractor authHandler) {
        this.authHandler = authHandler;
    }

    @PostConstruct
    public void init() {
        Flux<ProcessedSensorDataDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<SensorData> getGeneralPublisher(AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .map(GPSDataMapper::transform);
    }

    public Flux<SensorData> getContentFilteredPublisher(String content, AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .filter(withContent(content))
                .map(GPSDataMapper::transform);
    }

    public Flux<SensorData> getSinglePublisher(List<String> ids, AccessTokenDTO claims) {
        return dataPublisher.filter(getDeviceDomainFilter(claims))
                .filter(withDeviceId(ids))
                .map(GPSDataMapper::transform);
    }

    public void publish(ProcessedSensorDataDTO data) {
        dataStream.next(data);
    }

    private Predicate<ProcessedSensorDataDTO> withContent(String content) {
        return gpsData -> gpsData.device.records.entry
                .stream()
                .anyMatch(e -> e.content.contains(content));
    }

    private Predicate<ProcessedSensorDataDTO> withDeviceId(List<String> ids) {
        return gpsData -> ids.contains(gpsData.device.id.toString());
    }

    private Predicate<? super ProcessedSensorDataDTO> getDeviceDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:read"))
            throw new UnauthorizedException("No Permissions");

        return withDomain(extract.domains.stream().map(UUID::fromString).toList());
    }

    private Predicate<? super ProcessedSensorDataDTO> withDomain(List<UUID> domainIds) {
        return s -> s.device.domains.readWrite.stream().anyMatch(domainIds::contains) ||
                s.device.domains.read.stream().anyMatch(domainIds::contains);
    }
}

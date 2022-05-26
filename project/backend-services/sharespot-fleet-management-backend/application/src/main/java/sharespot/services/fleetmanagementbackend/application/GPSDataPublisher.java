package sharespot.services.fleetmanagementbackend.application;

import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
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

    private FluxSink<SensorDataDTO> dataStream;

    private ConnectableFlux<SensorDataDTO> dataPublisher;

    private final TokenExtractor authHandler;

    public GPSDataPublisher(TokenExtractor authHandler) {
        this.authHandler = authHandler;
    }

    @PostConstruct
    public void init() {
        Flux<SensorDataDTO> publisher = Flux.create(emitter -> dataStream = emitter);

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

    public void publish(SensorDataDTO data) {
        dataStream.next(data);
    }

    private Predicate<SensorDataDTO> withContent(String content) {
        return gpsData -> gpsData.device.records
                .stream()
                .anyMatch(e -> e.content.contains(content));
    }

    private Predicate<SensorDataDTO> withDeviceId(List<String> ids) {
        return gpsData -> ids.contains(gpsData.device.id.toString());
    }

    private Predicate<? super SensorDataDTO> getDeviceDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:live_data:read"))
            throw new UnauthorizedException("No Permissions");

        return withDomain(extract.domains.stream().map(UUID::fromString).toList());
    }

    private Predicate<? super SensorDataDTO> withDomain(List<UUID> domainIds) {
        return s -> s.device.domains.stream().anyMatch(domainIds::contains);
    }
}

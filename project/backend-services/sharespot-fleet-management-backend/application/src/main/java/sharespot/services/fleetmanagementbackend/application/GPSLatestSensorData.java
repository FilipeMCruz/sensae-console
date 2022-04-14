package sharespot.services.fleetmanagementbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.fleetmanagementbackend.application.auth.AccessTokenDTO;
import sharespot.services.fleetmanagementbackend.application.auth.TokenExtractor;
import sharespot.services.fleetmanagementbackend.application.auth.UnauthorizedException;
import sharespot.services.fleetmanagementbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.fleetmanagementbackend.domain.model.domain.DomainId;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GPSLatestSensorData {

    private final ProcessedSensorDataRepository repository;

    private final TokenExtractor authHandler;

    public GPSLatestSensorData(ProcessedSensorDataRepository repository, TokenExtractor authHandler) {
        this.repository = repository;
        this.authHandler = authHandler;
    }

    public Stream<SensorData> latest(List<String> devices, AccessTokenDTO claims) {
        return repository.lastDataOfEachDevice(extractDomainIds(claims))
                .map(GPSDataMapper::transform)
                .filter(s -> devices.contains(s.device().id().toString()));
    }

    public Stream<SensorData> latest(AccessTokenDTO claims) {
        return repository.lastDataOfEachDevice(extractDomainIds(claims))
                .map(GPSDataMapper::transform);
    }

    private Stream<DomainId> extractDomainIds(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:past_data:read"))
            throw new UnauthorizedException("No Permissions");

        return extract.domains.stream().distinct().map(d -> DomainId.of(UUID.fromString(d)));
    }
}

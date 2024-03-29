package pt.sensae.services.fleet.management.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.fleet.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.fleet.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.fleet.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.fleet.management.backend.domain.SensorDataRepository;
import pt.sensae.services.fleet.management.backend.domain.model.domain.DomainId;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.SensorData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GPSLatestSensorData {

    private final SensorDataRepository repository;

    private final TokenExtractor authHandler;

    public GPSLatestSensorData(SensorDataRepository repository, TokenExtractor authHandler) {
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
        if (!extract.permissions.contains("fleet_management:latest_data:read"))
            throw new UnauthorizedException("No Permissions");

        return extract.domains.stream().distinct().map(d -> DomainId.of(UUID.fromString(d)));
    }
}

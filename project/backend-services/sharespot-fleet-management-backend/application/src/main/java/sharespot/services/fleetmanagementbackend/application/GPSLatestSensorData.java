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
import java.util.stream.Collectors;

@Service
public class GPSLatestSensorData {

    private final ProcessedSensorDataRepository repository;

    private final TokenExtractor authHandler;

    public GPSLatestSensorData(ProcessedSensorDataRepository repository, TokenExtractor authHandler) {
        this.repository = repository;
        this.authHandler = authHandler;
    }

    public List<SensorData> latest(List<String> devices, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:read"))
            throw new UnauthorizedException("No Permissions");

        var domains = extract.domains.stream()
                .map(d -> DomainId.of(UUID.fromString(d)))
                .collect(Collectors.toSet());
        
        return repository.lastDataOfEachDevice(domains)
                .stream()
                .map(GPSDataMapper::transform)
                .filter(s -> devices.contains(s.device().id().toString()))
                .collect(Collectors.toList());
    }

    public List<SensorData> latest(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:read"))
            throw new UnauthorizedException("No Permissions");

        var domains = extract.domains.stream()
                .map(d -> DomainId.of(UUID.fromString(d)))
                .collect(Collectors.toSet());
        
        return repository.lastDataOfEachDevice(domains)
                .stream()
                .map(GPSDataMapper::transform)
                .collect(Collectors.toList());
    }
}

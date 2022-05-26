package pt.sensae.services.smart.irrigation.backend.application.services.command;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ValveCommand;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command.DeviceCommand;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValvePayload;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.DeviceCache;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.LatestDataCollector;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.model.LatestDataQuery;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Service
public class ValveSwitcherService {

    private final LatestDataCollector dataCollector;

    private final DeviceCommandPublisher publisher;

    private final TokenExtractor authHandler;

    private final DeviceCache cache;

    public ValveSwitcherService(LatestDataCollector dataCollector, DeviceCommandPublisher publisher, TokenExtractor authHandler, DeviceCache cache) {
        this.dataCollector = dataCollector;
        this.publisher = publisher;
        this.authHandler = authHandler;
        this.cache = cache;
    }

    public Boolean switchValve(String deviceId, AccessTokenDTO claims) {
        var ownership = Ownership.of(getDomainFilter(claims));
        var id = new DeviceId(UUID.fromString(deviceId));

        if (!this.cache.tryToSwitchValve(id)) {
            return false;
        }
        var success = new AtomicBoolean(false);
        dataCollector.fetch(new LatestDataQuery(new HashSet<>(), Set.of(id), ownership))
                .findFirst()
                .flatMap(data -> data.ledger().entries().stream().findFirst())
                .filter(e -> e.content().remoteControl().value())
                .flatMap(entry -> entry.dataHistory()
                        .stream()
                        .findFirst()
                        .filter(d -> d.payload() instanceof ValvePayload)
                        .map(d -> (ValvePayload) d.payload()))
                .ifPresent(valve -> {
                    success.compareAndSet(false, true);
                    publisher.publish(new DeviceCommand(ValveCommand.from(valve.status().value()), id));
                });
        return success.get();
    }

    private Stream<DomainId> getDomainFilter(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:valve:control"))
            throw new UnauthorizedException("No Permissions");

        return extract.domains.stream().map(UUID::fromString).map(DomainId::new);
    }
}

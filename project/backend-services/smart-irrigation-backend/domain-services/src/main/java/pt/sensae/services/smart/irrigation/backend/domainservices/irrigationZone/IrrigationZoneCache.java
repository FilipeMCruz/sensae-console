package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IrrigationZoneCache {

    private final Cache<IrrigationZoneId, IrrigationZone> cache;

    private final IrrigationZoneRepository repository;

    public IrrigationZoneCache(IrrigationZoneRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    public Stream<IrrigationZone> fetchAll(Ownership ownership) {
        return repository.fetchAll(ownership);
    }

    public Stream<IrrigationZone> fetchByIds(Stream<IrrigationZoneId> ids) {
        var irrigationZoneIds = ids.toList();
        if (irrigationZoneIds.isEmpty()) {
            return Stream.empty();
        }
        var allPresent = this.cache.getAllPresent(irrigationZoneIds);
        var toFetch = irrigationZoneIds.stream().filter(id -> !allPresent.containsKey(id));
        var irrigationZones = repository.fetchMultiple(toFetch).collect(Collectors.toSet());
        this.cache.putAll(irrigationZones.stream().collect(Collectors.toMap(IrrigationZone::id, entry -> entry)));
        return Stream.concat(irrigationZones.stream(), allPresent.values().stream());
    }

    public void store(IrrigationZone irrigationZone) {
        var save = this.repository.save(irrigationZone);
        this.cache.put(save.id(), save);
    }

    public void delete(IrrigationZoneId of) {
        this.repository.delete(of);
        this.cache.invalidate(of);
    }
}

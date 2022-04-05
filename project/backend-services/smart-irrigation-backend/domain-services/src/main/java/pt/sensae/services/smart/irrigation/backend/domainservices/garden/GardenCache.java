package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Garden;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenRepository;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GardenCache {

    private final Cache<GardenId, Garden> cache;

    private final GardenRepository repository;

    public GardenCache(GardenRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    public Stream<Garden> fetchByIds(Stream<GardenId> ids) {
        var allPresent = this.cache.getAllPresent(ids.toList());
        var toFetch = ids.filter(id -> !allPresent.containsKey(id));
        var gardens = repository.fetchMultiple(toFetch).collect(Collectors.toSet());
        this.cache.putAll(gardens.stream().collect(Collectors.toMap(Garden::id, entry -> entry)));
        return Stream.concat(gardens.stream(), allPresent.values().stream());
    }

    public void store(Garden garden) {
        var save = this.repository.save(garden);
        this.cache.put(save.id(), save);
    }
}

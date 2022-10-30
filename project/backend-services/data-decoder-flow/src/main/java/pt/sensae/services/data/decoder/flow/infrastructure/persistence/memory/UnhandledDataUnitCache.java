package pt.sensae.services.data.decoder.flow.infrastructure.persistence.memory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;
import pt.sensae.services.data.decoder.flow.domain.UnHandledDataUnitRepository;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UnhandledDataUnitCache implements UnHandledDataUnitRepository {
    private final Cache<SensorTypeId, Set<MessageConsumed<ObjectNode, DataRoutingKeys>>> cache;

    public UnhandledDataUnitCache(@ConfigProperty(name = "sensae.cache.unhandled.data.maxsize") int maxSizeCache) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(maxSizeCache)
                .build();
    }

    public void insert(MessageConsumed<ObjectNode, DataRoutingKeys> data, SensorTypeId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new HashSet<MessageConsumed<ObjectNode, DataRoutingKeys>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public Set<MessageConsumed<ObjectNode, DataRoutingKeys>> retrieve(SensorTypeId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new HashSet<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

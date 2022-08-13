package pt.sensae.services.data.processor.flow.infrastructure.persistence.memory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
import pt.sensae.services.data.processor.flow.domain.UnHandledDataUnitRepository;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UnhandledDataUnitCache implements UnHandledDataUnitRepository {
    private final Cache<SensorTypeId, Set<MessageConsumed<ObjectNode, SensorRoutingKeys>>> cache;

    public UnhandledDataUnitCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    public void insert(MessageConsumed<ObjectNode, SensorRoutingKeys> data, SensorTypeId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new HashSet<MessageConsumed<ObjectNode, SensorRoutingKeys>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public Set<MessageConsumed<ObjectNode, SensorRoutingKeys>> retrieve(SensorTypeId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new HashSet<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

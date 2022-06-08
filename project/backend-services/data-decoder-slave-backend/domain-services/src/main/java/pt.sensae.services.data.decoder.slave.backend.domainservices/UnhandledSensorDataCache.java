package sharespot.services.data.decoder.domainservices;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sensae.services.data.decoder.slave.backend.domain.SensorTypeId;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnhandledSensorDataCache {
    private final Cache<SensorTypeId, Set<MessageConsumed<ObjectNode, SensorRoutingKeys>>> cache;

    public UnhandledSensorDataCache() {
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

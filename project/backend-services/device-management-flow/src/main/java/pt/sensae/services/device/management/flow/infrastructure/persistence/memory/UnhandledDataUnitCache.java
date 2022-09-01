package pt.sensae.services.device.management.flow.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.device.management.flow.domain.UnHandledDataUnitRepository;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UnhandledDataUnitCache implements UnHandledDataUnitRepository {
    private final Cache<DeviceId, Set<MessageConsumed<DataUnitDTO, DataRoutingKeys>>> cache;

    public UnhandledDataUnitCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    public void insert(MessageConsumed<DataUnitDTO, DataRoutingKeys> data, DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new HashSet<MessageConsumed<DataUnitDTO, DataRoutingKeys>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public Set<MessageConsumed<DataUnitDTO, DataRoutingKeys>> retrieve(DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new HashSet<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

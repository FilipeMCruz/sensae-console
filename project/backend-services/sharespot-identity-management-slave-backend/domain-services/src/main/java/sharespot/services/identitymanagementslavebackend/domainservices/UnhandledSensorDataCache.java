package sharespot.services.identitymanagementslavebackend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnhandledSensorDataCache {
    private final Cache<DeviceId, Set<MessageConsumed<ProcessedSensorDataDTO>>> cache;

    public UnhandledSensorDataCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    public void insert(MessageConsumed<ProcessedSensorDataDTO> data, DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new HashSet<MessageConsumed<ProcessedSensorDataDTO>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public Set<MessageConsumed<ProcessedSensorDataDTO>> retrieve(DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new HashSet<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

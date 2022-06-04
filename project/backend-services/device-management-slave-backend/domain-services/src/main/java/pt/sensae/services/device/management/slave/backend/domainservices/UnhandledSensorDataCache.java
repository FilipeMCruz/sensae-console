package pt.sensae.services.device.management.slave.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UnhandledSensorDataCache {
    private final Cache<DeviceId, Set<MessageConsumed<SensorDataDTO, SensorRoutingKeys>>> cache;

    public UnhandledSensorDataCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    public void insert(MessageConsumed<SensorDataDTO, SensorRoutingKeys> data, DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new HashSet<MessageConsumed<SensorDataDTO, SensorRoutingKeys>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public Set<MessageConsumed<SensorDataDTO, SensorRoutingKeys>> retrieve(DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new HashSet<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

package pt.sensae.services.device.management.slave.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnhandledSensorDataCache {
    private final Cache<DeviceId, List<MessageConsumed<ProcessedSensorDataDTO>>> cache;

    public UnhandledSensorDataCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    public void insert(MessageConsumed<ProcessedSensorDataDTO> data, DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new ArrayList<MessageConsumed<ProcessedSensorDataDTO>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public List<MessageConsumed<ProcessedSensorDataDTO>> retrieve(DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new ArrayList<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

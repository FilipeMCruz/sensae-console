package pt.sensae.services.device.ownership.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnhandledAlertCache {
    private final Cache<DeviceId, Set<MessageConsumed<AlertDTO, AlertRoutingKeys>>> cache;

    public UnhandledAlertCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(200)
                .build();
    }

    public void insert(MessageConsumed<AlertDTO, AlertRoutingKeys> data, DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new HashSet<MessageConsumed<AlertDTO, AlertRoutingKeys>>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public Set<MessageConsumed<AlertDTO, AlertRoutingKeys>> retrieve(DeviceId id) {
        var alerts = this.cache.getIfPresent(id);
        if (alerts == null) {
            return new HashSet<>();
        } else {
            this.cache.invalidate(id);
            return alerts.stream()
                    .filter(alert -> alert.data.context
                            .deviceIds.stream()
                            .map(DeviceId::new)
                            .allMatch(dev -> cache.getIfPresent(dev) == null))
                    .collect(Collectors.toSet());
        }
    }
}

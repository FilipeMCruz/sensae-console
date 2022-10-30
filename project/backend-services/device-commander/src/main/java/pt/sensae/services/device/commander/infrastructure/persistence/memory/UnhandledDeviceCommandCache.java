package pt.sensae.services.device.commander.infrastructure.persistence.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pt.sensae.services.device.commander.domain.UnHandledDeviceCommandRepository;
import pt.sensae.services.device.commander.domain.commands.DeviceCommandReceived;
import pt.sensae.services.device.commander.domain.device.DeviceId;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UnhandledDeviceCommandCache implements UnHandledDeviceCommandRepository {
    private final Cache<DeviceId, List<DeviceCommandReceived>> cache;

    public UnhandledDeviceCommandCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(10)
                .build();
    }

    public void insert(DeviceCommandReceived data, DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            var list = new ArrayList<DeviceCommandReceived>();
            list.add(data);
            this.cache.put(id, list);
        } else {
            ifPresent.add(data);
        }
    }

    public List<DeviceCommandReceived> retrieve(DeviceId id) {
        var ifPresent = this.cache.getIfPresent(id);
        if (ifPresent == null) {
            return new ArrayList<>();
        } else {
            this.cache.invalidate(id);
            return ifPresent;
        }
    }
}

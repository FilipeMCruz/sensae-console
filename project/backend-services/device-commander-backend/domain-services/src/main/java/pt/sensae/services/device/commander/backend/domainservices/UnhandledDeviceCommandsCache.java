package pt.sensae.services.device.commander.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommandReceived;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnhandledDeviceCommandsCache {
    private final Cache<DeviceId, List<DeviceCommandReceived>> cache;

    public UnhandledDeviceCommandsCache() {
        this.cache = Caffeine.newBuilder().maximumSize(5).build();
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

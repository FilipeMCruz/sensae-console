package pt.sensae.services.device.management.slave.backend.domain.model.staticData;

import java.util.HashSet;
import java.util.Set;

public record DeviceStaticData(Set<DeviceStaticDataEntry> entries) {

    public static DeviceStaticData empty() {
        return new DeviceStaticData(new HashSet<>());
    }
}

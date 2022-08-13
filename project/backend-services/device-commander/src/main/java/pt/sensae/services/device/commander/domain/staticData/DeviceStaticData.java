package pt.sensae.services.device.commander.domain.staticData;

import java.util.HashSet;
import java.util.Set;

public record DeviceStaticData(Set<DeviceStaticDataEntry> entries) {

    public static DeviceStaticData empty() {
        return new DeviceStaticData(new HashSet<>());
    }
}

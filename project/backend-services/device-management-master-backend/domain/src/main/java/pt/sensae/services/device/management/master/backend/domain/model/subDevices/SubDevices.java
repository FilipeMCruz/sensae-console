package pt.sensae.services.device.management.master.backend.domain.model.subDevices;

import java.util.HashSet;
import java.util.Set;

public record SubDevices(Set<SubDevice> entries) {
    public static SubDevices empty() {
        return new SubDevices(new HashSet<>());
    }
}

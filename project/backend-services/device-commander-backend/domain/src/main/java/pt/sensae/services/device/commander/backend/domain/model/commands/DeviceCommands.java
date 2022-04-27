package pt.sensae.services.device.commander.backend.domain.model.commands;

import java.util.HashSet;
import java.util.Set;

public record DeviceCommands(Set<CommandEntry> entries) {
    public static DeviceCommands empty() {
        return new DeviceCommands(new HashSet<>());
    }
}

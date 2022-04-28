package pt.sensae.services.device.management.slave.backend.domain.model.commands;

public record CommandPort(Integer value) {
    public static CommandPort of(Integer value) {
        return new CommandPort(value);
    }
}

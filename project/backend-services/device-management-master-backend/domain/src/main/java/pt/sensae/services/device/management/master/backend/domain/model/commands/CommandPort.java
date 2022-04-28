package pt.sensae.services.device.management.master.backend.domain.model.commands;

public record CommandPort(Integer value) {
    public static CommandPort of(Integer value) {
        return new CommandPort(value);
    }
}

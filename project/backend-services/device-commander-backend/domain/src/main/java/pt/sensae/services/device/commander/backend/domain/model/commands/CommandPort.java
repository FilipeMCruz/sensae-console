package pt.sensae.services.device.commander.backend.domain.model.commands;

public record CommandPort(Integer value) {
    public static CommandPort of(Integer value) {
        return new CommandPort(value);
    }
}

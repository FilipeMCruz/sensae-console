package pt.sensae.services.device.commander.backend.domain.model.commands;

public record CommandId(String value) {
    public static CommandId of(String value) {
        return new CommandId(value);
    }
}

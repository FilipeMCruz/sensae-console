package pt.sensae.services.device.management.flow.domain.commands;

public record CommandId(String value) {
    public static CommandId of(String value) {
        return new CommandId(value);
    }
}

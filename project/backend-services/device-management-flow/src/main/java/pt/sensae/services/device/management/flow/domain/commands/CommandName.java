package pt.sensae.services.device.management.flow.domain.commands;

public record CommandName(String value) {
    public static CommandName of(String value) {
        return new CommandName(value);
    }
}

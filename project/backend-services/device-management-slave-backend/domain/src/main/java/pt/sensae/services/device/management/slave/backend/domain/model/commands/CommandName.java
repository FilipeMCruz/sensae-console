package pt.sensae.services.device.management.slave.backend.domain.model.commands;

public record CommandName(String value) {
    public static CommandName of(String value) {
        return new CommandName(value);
    }
}

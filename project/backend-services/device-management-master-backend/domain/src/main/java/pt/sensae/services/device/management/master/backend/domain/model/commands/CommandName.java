package pt.sensae.services.device.management.master.backend.domain.model.commands;

public record CommandName(String value) {
    public static CommandName of(String value) {
        return new CommandName(value);
    }
}

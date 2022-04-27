package pt.sensae.services.device.management.master.backend.domain.model.commands;

public record CommandId(String value) {
    public static CommandId of(String value) {
        return new CommandId(value);
    }
}

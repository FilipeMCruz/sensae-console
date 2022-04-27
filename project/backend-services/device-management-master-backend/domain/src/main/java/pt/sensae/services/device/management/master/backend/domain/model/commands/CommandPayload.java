package pt.sensae.services.device.management.master.backend.domain.model.commands;

public record CommandPayload(String value) {
    public static CommandPayload of(String value) {
        return new CommandPayload(value);
    }
}

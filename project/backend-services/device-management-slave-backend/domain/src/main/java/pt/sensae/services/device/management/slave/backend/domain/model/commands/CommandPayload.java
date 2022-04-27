package pt.sensae.services.device.management.slave.backend.domain.model.commands;

public record CommandPayload(String value) {
    public static CommandPayload of(String value) {
        return new CommandPayload(value);
    }
}

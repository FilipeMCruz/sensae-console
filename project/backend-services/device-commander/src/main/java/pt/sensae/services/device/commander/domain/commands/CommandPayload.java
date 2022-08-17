package pt.sensae.services.device.commander.domain.commands;

public record CommandPayload(String value) {
    public static CommandPayload of(String value) {
        return new CommandPayload(value);
    }
}

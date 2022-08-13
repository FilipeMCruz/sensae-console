package pt.sensae.services.device.management.flow.domain.commands;

public record CommandPort(Integer value) {
    public static CommandPort of(Integer value) {
        return new CommandPort(value);
    }
}

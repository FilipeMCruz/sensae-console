package pt.sensae.services.device.commander.domain.commands;

import pt.sensae.services.device.commander.domain.commands.request.CommandRequestDTO;
import pt.sensae.services.device.commander.domain.subDevices.DeviceRef;

public record CommandEntry(CommandId id, CommandName name, CommandPayload payload, CommandPort port, DeviceRef ref) {
    public CommandRequestDTO toCommandRequest() {
        var request = new CommandRequestDTO();
        request.confirmed = false;
        request.port = port.value();
        request.payload_raw = payload.value();
        return request;
    }
}

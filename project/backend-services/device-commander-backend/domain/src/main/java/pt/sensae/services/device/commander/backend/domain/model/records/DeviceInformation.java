package pt.sensae.services.device.commander.backend.domain.model.records;

import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.commander.backend.domain.model.device.Device;
import pt.sensae.services.device.commander.backend.domain.model.subDevices.SubDevices;

public record DeviceInformation(Device device,
                                SubDevices subDevices,
                                DeviceCommands commands) {
}

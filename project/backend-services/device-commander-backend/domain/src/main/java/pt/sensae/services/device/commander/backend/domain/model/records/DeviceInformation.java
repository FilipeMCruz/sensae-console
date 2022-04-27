package pt.sensae.services.device.commander.backend.domain.model.records;

import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.commander.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.commander.backend.domain.model.device.Device;

public record DeviceInformation(Device device,
                                Records records,
                                SubDevices subDevices,
                                DeviceCommands commands) {
}

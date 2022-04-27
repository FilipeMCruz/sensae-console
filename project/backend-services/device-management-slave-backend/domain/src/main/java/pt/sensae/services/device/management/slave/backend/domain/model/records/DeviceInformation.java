package pt.sensae.services.device.management.slave.backend.domain.model.records;

import pt.sensae.services.device.management.slave.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;

public record DeviceInformation(Device device,
                                Records records,
                                SubDevices subDevices,
                                DeviceCommands commands) {
}

package pt.sensae.services.device.management.master.backend.domain.model.records;

import pt.sensae.services.device.management.master.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;

public record DeviceInformation(Device device,
                                Records records,
                                SubDevices subDevices,
                                DeviceCommands commands) {
}

package pt.sensae.services.device.management.master.backend.domain.model;

import pt.sensae.services.device.management.master.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.master.backend.domain.model.records.Records;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;

public record DeviceInformation(Device device,
                                Records records,
                                SubDevices subDevices,
                                DeviceCommands commands) {
    public DeviceInformation withDownlink(DeviceDownlink downlink) {
        var device1 = new Device(device.id(), device.name(), downlink);
        return new DeviceInformation(device1, records, subDevices, commands);
    }
}

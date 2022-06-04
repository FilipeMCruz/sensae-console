package pt.sensae.services.device.management.master.backend.domain.model;

import pt.sensae.services.device.management.master.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.DeviceStaticData;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;

public record DeviceInformation(Device device,
                                DeviceRecords deviceRecords,
                                DeviceStaticData staticData,
                                SubDevices subDevices,
                                DeviceCommands commands) {
    public DeviceInformation withDownlink(DeviceDownlink downlink) {
        var device1 = new Device(device.id(), device.name(), downlink);
        return new DeviceInformation(device1, deviceRecords, staticData, subDevices, commands);
    }
}

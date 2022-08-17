package pt.sensae.services.device.management.flow.domain;

import pt.sensae.services.device.management.flow.domain.commands.DeviceCommands;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sensae.services.device.management.flow.domain.device.DeviceDownlink;
import pt.sensae.services.device.management.flow.domain.records.DeviceRecords;
import pt.sensae.services.device.management.flow.domain.staticData.DeviceStaticData;
import pt.sensae.services.device.management.flow.domain.subDevices.SubDevices;

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

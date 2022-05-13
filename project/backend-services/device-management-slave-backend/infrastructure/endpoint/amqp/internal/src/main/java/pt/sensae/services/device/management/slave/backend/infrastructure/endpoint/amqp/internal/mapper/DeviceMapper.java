package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.application.DeviceEventMapper;
import pt.sensae.services.device.management.slave.backend.application.DeviceNotificationDTO;
import pt.sensae.services.device.management.slave.backend.domain.model.commands.*;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.slave.backend.domain.model.notification.DeviceNotification;
import pt.sensae.services.device.management.slave.backend.domain.model.notification.NotificationType;
import pt.sensae.services.device.management.slave.backend.domain.model.records.*;
import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.DeviceRef;
import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.SubDevice;
import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.model.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceMapper implements DeviceEventMapper {

    @Override
    public DeviceDTOImpl domainToDto(Device device) {
        var dto = new DeviceDTOImpl();
        dto.deviceId = device.id().value().toString();
        dto.name = device.name().value();
        dto.downlink = device.downlink().value();
        return dto;
    }

    @Override
    public DeviceNotification dtoToDomain(DeviceNotificationDTO dto) {
        var notification = (DeviceNotificationDTOImpl) dto;
        if (notification.type.equals(DeviceNotificationTypeDTOImpl.UPDATE)) {
            var device = notification.information;

            List<RecordEntry> collect = device.entries.stream()
                    .map(e -> e.type.equals(DeviceRecordEntryTypeDTOImpl.SENSOR_DATA) ? new SensorDataRecordEntry(SensorDataRecordLabel.give(e.label), e.content) : new BasicRecordEntry(e.label, e.content))
                    .collect(Collectors.toList());

            var deviceId = new DeviceId(UUID.fromString(device.deviceId));
            var deviceName = new DeviceName(device.name);
            var deviceDownlink = new DeviceDownlink(device.downlink);

            var subDevices = device.subSensors.stream()
                    .map(sub -> new SubDevice(new DeviceId(UUID.fromString(sub.subDeviceId)), new DeviceRef(sub.subDeviceRef)))
                    .collect(Collectors.toSet());

            var commands = device.commands.stream()
                    .map(c -> new CommandEntry(CommandId.of(c.id), CommandName.of(c.name), CommandPayload.of(c.payload), CommandPort.of(c.port), DeviceRef.of(c.subDeviceRef)))
                    .collect(Collectors.toSet());

            var info = new DeviceInformation(new Device(deviceId, deviceName, deviceDownlink), new Records(collect), new SubDevices(subDevices), new DeviceCommands(commands));
            return new DeviceNotification(deviceId, NotificationType.UPDATE, info);
        } else {
            var deviceId = new DeviceId(UUID.fromString(notification.deviceId));
            return new DeviceNotification(deviceId, NotificationType.DELETE, null);
        }
    }
}

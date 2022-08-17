package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.mapper;


import pt.sensae.services.device.management.flow.application.mapper.DeviceEventMapper;
import pt.sensae.services.device.management.flow.application.model.DeviceNotificationDTO;
import pt.sensae.services.device.management.flow.domain.DeviceInformation;
import pt.sensae.services.device.management.flow.domain.commands.*;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sensae.services.device.management.flow.domain.device.DeviceDownlink;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;
import pt.sensae.services.device.management.flow.domain.device.DeviceName;
import pt.sensae.services.device.management.flow.domain.notification.DeviceNotification;
import pt.sensae.services.device.management.flow.domain.notification.NotificationType;
import pt.sensae.services.device.management.flow.domain.records.BasicRecordEntry;
import pt.sensae.services.device.management.flow.domain.records.DeviceRecords;
import pt.sensae.services.device.management.flow.domain.staticData.DeviceStaticData;
import pt.sensae.services.device.management.flow.domain.staticData.DeviceStaticDataEntry;
import pt.sensae.services.device.management.flow.domain.staticData.StaticDataLabel;
import pt.sensae.services.device.management.flow.domain.subDevices.DeviceRef;
import pt.sensae.services.device.management.flow.domain.subDevices.SubDevice;
import pt.sensae.services.device.management.flow.domain.subDevices.SubDevices;
import pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model.DeviceDTOImpl;
import pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;
import pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationTypeDTOImpl;
import pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model.DeviceStaticDataLabelDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
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
        var deviceId = new DeviceId(UUID.fromString(notification.deviceId));

        if (notification.type.equals(DeviceNotificationTypeDTOImpl.UPDATE)) {
            var device = notification.information;

            Set<BasicRecordEntry> collect = device.records.stream()
                    .map(e -> new BasicRecordEntry(e.label, e.content))
                    .collect(Collectors.toSet());

            Set<DeviceStaticDataEntry> staticData = device.staticData.stream()
                    .map(e -> new DeviceStaticDataEntry(dtoToModel(e.label), e.content))
                    .collect(Collectors.toSet());

            var deviceName = new DeviceName(device.name);
            var deviceDownlink = new DeviceDownlink(device.downlink);

            var subDevices = device.subSensors.stream()
                    .map(sub -> new SubDevice(new DeviceId(UUID.fromString(sub.subDeviceId)), new DeviceRef(sub.subDeviceRef)))
                    .collect(Collectors.toSet());

            var commands = device.commands.stream()
                    .map(c -> new CommandEntry(CommandId.of(c.id), CommandName.of(c.name), CommandPayload.of(c.payload), CommandPort.of(c.port), DeviceRef.of(c.subDeviceRef)))
                    .collect(Collectors.toSet());

            var info = new DeviceInformation(new Device(deviceId, deviceName, deviceDownlink), new DeviceRecords(collect), new DeviceStaticData(staticData), new SubDevices(subDevices), new DeviceCommands(commands));
            return new DeviceNotification(deviceId, NotificationType.UPDATE, info);
        } else {
            return new DeviceNotification(deviceId, NotificationType.DELETE, null);
        }
    }

    private StaticDataLabel dtoToModel(DeviceStaticDataLabelDTOImpl model) {
        return switch (model) {
            case GPS_LATITUDE -> StaticDataLabel.GPS_LATITUDE;
            case GPS_LONGITUDE -> StaticDataLabel.GPS_LONGITUDE;
            case GPS_ALTITUDE -> StaticDataLabel.GPS_ALTITUDE;
            case BATTERY_MIN_VOLTS -> StaticDataLabel.BATTERY_MIN_VOLTS;
            case BATTERY_MAX_VOLTS -> StaticDataLabel.BATTERY_MAX_VOLTS;
            case MAX_DISTANCE -> StaticDataLabel.MAX_DISTANCE;
            case MIN_DISTANCE -> StaticDataLabel.MIN_DISTANCE;
        };
    }
}

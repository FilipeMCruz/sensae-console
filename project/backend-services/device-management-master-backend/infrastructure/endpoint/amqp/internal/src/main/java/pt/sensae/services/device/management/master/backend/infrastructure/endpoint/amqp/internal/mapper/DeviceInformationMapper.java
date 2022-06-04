package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceDTO;
import pt.sensae.services.device.management.master.backend.application.DeviceEventMapper;
import pt.sensae.services.device.management.master.backend.application.DeviceNotificationDTO;
import pt.sensae.services.device.management.master.backend.application.ownership.DeviceWithAllOwnerDomains;
import pt.sensae.services.device.management.master.backend.application.ownership.DomainId;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.StaticDataLabel;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.*;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceInformationMapper implements DeviceEventMapper {

    @Override
    public DeviceNotificationDTO domainToUpdatedDto(DeviceInformation domain) {
        var info = new DeviceInformationDTOImpl();
        info.deviceId = domain.device().id().value().toString();
        info.name = domain.device().name().value();
        info.downlink = domain.device().downlink().value();
        if (info.downlink == null) info.downlink = "";

        info.records = domain.deviceRecords().entries().stream().map(e -> {
            var entry = new DeviceRecordEntryDTOImpl();
            entry.content = e.content();
            entry.label = e.label();
            return entry;
        }).collect(Collectors.toSet());

        info.staticData = domain.staticData().entries().stream().map(e -> {
            var entry = new DeviceStaticSensorEntryDTOImpl();
            entry.content = e.content();
            entry.label = modelToDto(e.label());
            return entry;
        }).collect(Collectors.toSet());

        info.subSensors = domain.subDevices().entries().stream().map(sub -> {
            var entry = new DeviceSubSensorDTOImpl();
            entry.subDeviceRef = sub.ref().value();
            entry.subDeviceId = sub.id().value().toString();
            return entry;
        }).collect(Collectors.toSet());

        info.commands = domain.commands().entries().stream().map(com -> {
            var entry = new DeviceCommandEntryDTOImpl();
            entry.id = com.id().value();
            entry.name = com.name().value();
            entry.port = com.port().value();
            entry.payload = com.payload().value();
            entry.subDeviceRef = com.ref().value();
            return entry;
        }).collect(Collectors.toSet());

        var notification = new DeviceNotificationDTOImpl();
        notification.deviceId = info.deviceId;
        notification.type = DeviceNotificationTypeDTOImpl.UPDATE;
        notification.information = info;
        return notification;
    }

    private DeviceStaticDataLabelDTOImpl modelToDto(StaticDataLabel model) {
        return switch (model) {
            case GPS_LATITUDE -> DeviceStaticDataLabelDTOImpl.GPS_LATITUDE;
            case GPS_LONGITUDE -> DeviceStaticDataLabelDTOImpl.GPS_LONGITUDE;
            case GPS_ALTITUDE -> DeviceStaticDataLabelDTOImpl.GPS_ALTITUDE;
            case BATTERY_MAX_VOLTS -> DeviceStaticDataLabelDTOImpl.BATTERY_MAX_VOLTS;
            case BATTERY_MIN_VOLTS -> DeviceStaticDataLabelDTOImpl.BATTERY_MIN_VOLTS;
            case MIN_DISTANCE -> DeviceStaticDataLabelDTOImpl.MIN_DISTANCE;
            case MAX_DISTANCE -> DeviceStaticDataLabelDTOImpl.MAX_DISTANCE;
        };
    }

    @Override
    public DeviceNotificationDTO domainToDeletedDto(DeviceId domain) {
        var notification = new DeviceNotificationDTOImpl();
        notification.deviceId = domain.value().toString();
        notification.type = DeviceNotificationTypeDTOImpl.DELETE;
        return notification;
    }

    @Override
    public Device dtoToDomain(DeviceDTO dto) {
        var device = (DeviceDTOImpl) dto;
        return new Device(new DeviceId(UUID.fromString(device.deviceId)), new DeviceName(device.name), new DeviceDownlink(device.downlink));
    }

    public DeviceWithAllOwnerDomains dtoToDomain(DeviceIdentityDTOImpl dto) {
        var domainIds = dto.information.owners.stream()
                .map(UUID::fromString)
                .map(DomainId::of)
                .collect(Collectors.toSet());
        return new DeviceWithAllOwnerDomains(DeviceId.of(UUID.fromString(dto.deviceId)), domainIds);
    }
}
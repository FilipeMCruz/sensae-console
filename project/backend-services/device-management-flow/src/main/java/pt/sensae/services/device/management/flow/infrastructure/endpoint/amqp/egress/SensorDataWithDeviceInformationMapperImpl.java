package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.egress;

import pt.sensae.services.device.management.flow.application.mapper.SensorDataWithDeviceInformationMapper;
import pt.sensae.services.device.management.flow.domain.DeviceInformation;
import pt.sensae.services.device.management.flow.domain.DeviceWithSubDevices;
import pt.sensae.services.device.management.flow.domain.commands.CommandEntry;
import pt.sensae.services.device.management.flow.domain.staticData.DeviceStaticData;
import pt.sensae.services.device.management.flow.domain.staticData.StaticDataLabel;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.model.data.types.BatteryDataDTO;
import pt.sharespot.iot.core.sensor.model.data.types.DistanceDataDTO;
import pt.sharespot.iot.core.sensor.model.data.types.GPSDataDTO;
import pt.sharespot.iot.core.sensor.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.sensor.model.device.controls.DeviceCommandDTO;
import pt.sharespot.iot.core.sensor.model.device.records.DeviceRecordEntryDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class SensorDataWithDeviceInformationMapperImpl implements SensorDataWithDeviceInformationMapper {

    @Override
    public DeviceWithSubDevices domainToDto(SensorDataDTO domain, DeviceInformation deviceInformation) {
        var staticData = deviceInformation.staticData();

        addGPSData(domain, staticData);
        addBatteryData(domain, staticData);
        addDistanceData(domain, staticData);

        var newRecords = deviceInformation.deviceRecords()
                .entries()
                .stream()
                .map(e -> new DeviceRecordEntryDTO(e.label(), e.content()))
                .collect(Collectors.toSet());

        if (domain.hasProperty(PropertyName.DEVICE_RECORDS)) {
            domain.device.records.stream()
                    .filter(entry -> newRecords.stream().noneMatch(e -> Objects.equals(entry.label, e.label)))
                    .forEach(newRecords::add);
        }

        domain.device.records = newRecords;

        domain.device.name = deviceInformation.device().name().value();

        if (!domain.hasProperty(PropertyName.DEVICE_DOWNLINK)) {
            domain.device.downlink = deviceInformation.device().downlink().value();
        }

        if (domain.hasProperty(PropertyName.DEVICE_COMMANDS)) {
            domain.device.commands.forEach((devKey, value) -> {
                var commandEntries = deviceInformation.commands()
                        .entries()
                        .stream()
                        .filter(c -> Objects.equals(c.ref().value(), devKey))
                        .toList();

                value.stream().filter(com -> commandEntries.stream().noneMatch(c -> com.id.equals(c.id().value())))
                        .forEach(e -> domain.device.commands.computeIfAbsent(devKey, k -> new ArrayList<>()).add(e));
            });
        } else {
            domain.device.commands = new HashMap<>();
            deviceInformation.commands()
                    .entries()
                    .forEach(e -> domain.device.commands
                            .computeIfAbsent(e.ref().value(), k -> new ArrayList<>())
                            .add(this.processCommands(e)));
        }

        return new DeviceWithSubDevices(domain, processSubSensors(domain, deviceInformation));
    }

    private void addGPSData(SensorDataDTO domain, DeviceStaticData deviceInformation) {

        var latitudeOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.GPS_LATITUDE))
                .findFirst();

        var longitudeOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.GPS_LONGITUDE))
                .findFirst();

        var altitudeOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.GPS_ALTITUDE))
                .findFirst();

        if (domain.getSensorData().gps == null && (latitudeOpt.isPresent() || longitudeOpt.isPresent() || altitudeOpt.isPresent())) {
            domain.getSensorData().withGps(new GPSDataDTO());
        }

        latitudeOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().gps.latitude = Double.valueOf(deviceStaticDataEntry.content()));
        longitudeOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().gps.longitude = Double.valueOf(deviceStaticDataEntry.content()));
        altitudeOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().gps.altitude = Float.valueOf(deviceStaticDataEntry.content()));
    }

    private void addDistanceData(SensorDataDTO domain, DeviceStaticData deviceInformation) {

        var maxDistanceOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.MAX_DISTANCE))
                .findFirst();

        var minDistanceOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.MIN_DISTANCE))
                .findFirst();

        if (domain.getSensorData().distance == null && (maxDistanceOpt.isPresent() || minDistanceOpt.isPresent())) {
            domain.getSensorData().withDistance(new DistanceDataDTO());
        }

        maxDistanceOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().distance.maxMillimeters = Float.valueOf(deviceStaticDataEntry.content()));
        minDistanceOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().distance.minMillimeters = Float.valueOf(deviceStaticDataEntry.content()));
    }

    private void addBatteryData(SensorDataDTO domain, DeviceStaticData deviceInformation) {

        var maxBatOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.BATTERY_MAX_VOLTS))
                .findFirst();

        var minBatOpt = deviceInformation.entries().stream()
                .filter(e -> e.has(StaticDataLabel.BATTERY_MIN_VOLTS))
                .findFirst();

        if (domain.getSensorData().battery == null && (maxBatOpt.isPresent() || minBatOpt.isPresent())) {
            domain.getSensorData().withBattery(new BatteryDataDTO());
        }

        maxBatOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().battery.maxVolts = Float.valueOf(deviceStaticDataEntry.content()));
        minBatOpt.ifPresent(deviceStaticDataEntry -> domain.getSensorData().battery.minVolts = Float.valueOf(deviceStaticDataEntry.content()));
    }

    private DeviceCommandDTO processCommands(CommandEntry model) {
        var command = new DeviceCommandDTO();
        command.id = model.id().value();
        command.name = model.name().value();
        command.port = model.port().value();
        command.payload = model.payload().value();
        return command;
    }

    private List<SensorDataDTO> processSubSensors(SensorDataDTO domain, DeviceInformation records) {
        var subDevices = new ArrayList<SensorDataDTO>();

        records.subDevices().entries().forEach(sub -> {
            var subSensorMeasures = domain.measures.get(sub.ref().value());

            var subSensorCommands = records.commands()
                    .entries()
                    .stream()
                    .filter(com -> com.ref().equals(sub.ref()))
                    .map(this::processCommands)
                    .toList();

            var deviceInformationDTO = new DeviceInformationDTO();
            deviceInformationDTO.id = sub.id().value();
            deviceInformationDTO.downlink = domain.device.downlink;

            var sensorDataDTO = new SensorDataDTO();
            sensorDataDTO.device = deviceInformationDTO;
            sensorDataDTO.reportedAt = domain.reportedAt;
            sensorDataDTO.dataId = UUID.randomUUID();

            sensorDataDTO.measures = Map.of(0, Objects.requireNonNullElseGet(subSensorMeasures, SensorDataDetailsDTO::new));

            sensorDataDTO.device.commands = Map.of(0, Objects.requireNonNullElseGet(subSensorCommands, ArrayList::new));

            subDevices.add(sensorDataDTO);
        });

        return subDevices;
    }
}

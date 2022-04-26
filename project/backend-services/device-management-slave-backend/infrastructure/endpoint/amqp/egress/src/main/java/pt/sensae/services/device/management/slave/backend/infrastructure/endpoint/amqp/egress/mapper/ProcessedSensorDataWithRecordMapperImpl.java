package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.egress.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.application.ProcessedSensorDataWithRecordMapper;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.records.BasicRecordEntry;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.slave.backend.domain.model.records.SensorDataRecordEntry;
import pt.sensae.services.device.management.slave.backend.domain.model.records.SensorDataRecordLabel;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.types.GPSDataDTO;
import pt.sharespot.iot.core.sensor.device.DeviceInformationDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordBasicEntryDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessedSensorDataWithRecordMapperImpl implements ProcessedSensorDataWithRecordMapper {

    @Override
    public DeviceWithSubDevices domainToDto(ProcessedSensorDataDTO domain, DeviceRecords records) {
        var sensorDataToUpdate = records.records()
                .entries()
                .stream()
                .filter(e -> e instanceof SensorDataRecordEntry)
                .map(e -> (SensorDataRecordEntry) e)
                .collect(Collectors.toSet());

        var latitudeOpt = sensorDataToUpdate.stream()
                .filter(e -> e.has(SensorDataRecordLabel.GPS_LATITUDE))
                .findFirst();

        var longitudeOpt = sensorDataToUpdate.stream()
                .filter(e -> e.has(SensorDataRecordLabel.GPS_LONGITUDE))
                .findFirst();

        if (latitudeOpt.isPresent() && longitudeOpt.isPresent()) {
            domain.getSensorData()
                    .withGps(GPSDataDTO.ofLatLong(Double.valueOf(latitudeOpt.get()
                            .content()), Double.valueOf(longitudeOpt.get().content())));
        }

        domain.device.records = new DeviceRecordDTO(records.records()
                .entries()
                .stream()
                .filter(e -> e instanceof BasicRecordEntry)
                .map(e -> (BasicRecordEntry) e)
                .map(e -> new DeviceRecordBasicEntryDTO(e.label(), e.content()))
                .collect(Collectors.toSet()));

        domain.device.name = records.device().name().value();

        return new DeviceWithSubDevices(domain, processSubSensors(domain, records));
    }

    private List<ProcessedSensorDataDTO> processSubSensors(ProcessedSensorDataDTO domain, DeviceRecords records) {
        var subDevices = new ArrayList<ProcessedSensorDataDTO>();

        records.subDevices().entries().forEach(sub -> {
            var subSensorMeasures = domain.measures.get(sub.ref().value());
            if (subSensorMeasures != null) {
                var processedSensorDataDTO = new ProcessedSensorDataDTO();

                var deviceInformationDTO = new DeviceInformationDTO();
                deviceInformationDTO.id = sub.id().value();

                processedSensorDataDTO.device = deviceInformationDTO;
                processedSensorDataDTO.reportedAt = domain.reportedAt;
                processedSensorDataDTO.dataId = UUID.randomUUID();
                processedSensorDataDTO.measures = Map.of(0, subSensorMeasures);
                subDevices.add(processedSensorDataDTO);
            }
        });

        return subDevices;
    }
}

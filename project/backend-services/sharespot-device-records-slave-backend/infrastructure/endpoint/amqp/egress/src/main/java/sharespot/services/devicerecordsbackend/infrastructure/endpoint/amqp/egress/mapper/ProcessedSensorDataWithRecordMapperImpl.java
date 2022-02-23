package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordBasicEntryDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.BasicRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.SensorDataRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.SensorDataRecordLabel;

import java.util.stream.Collectors;

@Service
public class ProcessedSensorDataWithRecordMapperImpl implements ProcessedSensorDataWithRecordMapper {

    @Override
    public ProcessedSensorDataDTO domainToDto(ProcessedSensorDataDTO domain, DeviceRecords records) {
        var sensorDataToUpdate = records.records()
                .entries()
                .stream()
                .filter(e -> e instanceof SensorDataRecordEntry)
                .map(e -> (SensorDataRecordEntry) e)
                .collect(Collectors.toSet());

        sensorDataToUpdate.stream()
                .filter(e -> e.has(SensorDataRecordLabel.GPS_LATITUDE))
                .findFirst()
                .ifPresent(entry -> domain.data.gps.latitude = Double.parseDouble(entry.content()));

        sensorDataToUpdate.stream()
                .filter(e -> e.has(SensorDataRecordLabel.GPS_LONGITUDE))
                .findFirst()
                .ifPresent(entry -> domain.data.gps.longitude = Double.parseDouble(entry.content()));

        domain.device.records = new DeviceRecordDTO(records.records()
                .entries()
                .stream()
                .filter(e -> e instanceof BasicRecordEntry)
                .map(e -> (BasicRecordEntry) e)
                .map(e -> new DeviceRecordBasicEntryDTO(e.label(), e.content()))
                .collect(Collectors.toSet()));
        return domain;
    }
}

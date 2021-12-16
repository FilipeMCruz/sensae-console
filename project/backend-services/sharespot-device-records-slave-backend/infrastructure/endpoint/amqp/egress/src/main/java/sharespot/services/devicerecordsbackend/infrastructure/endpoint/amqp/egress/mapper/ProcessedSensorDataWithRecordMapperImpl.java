package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.device.DeviceInformationWithRecordsDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordBasicEntryDTO;
import pt.sharespot.iot.core.sensor.device.records.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.BasicRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.SensorDataRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.SensorDataRecordLabel;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorDataWithRecord;

import java.util.stream.Collectors;

@Service
public class ProcessedSensorDataWithRecordMapperImpl implements ProcessedSensorDataWithRecordMapper {

    @Override
    public ProcessedSensorDataWithRecordsDTO domainToDto(ProcessedSensorDataWithRecord domain) {
        var dataId = domain.getDataId();
        var device = domain.getDevice();
        var reportedAt = domain.getReportedAt();

        var sensorDataToUpdate = domain.getRecords()
                .entries()
                .stream()
                .filter(e -> e instanceof SensorDataRecordEntry)
                .map(e -> (SensorDataRecordEntry) e)
                .collect(Collectors.toSet());

        var sensorData = new SensorDataDetailsDTO();
        var gpsData = new GPSDataDTO();

        sensorDataToUpdate.stream()
                .filter(e -> e.has(SensorDataRecordLabel.GPS_LATITUDE))
                .findFirst()
                .ifPresentOrElse(
                        e -> gpsData.latitude = Double.parseDouble(e.content()),
                        () -> gpsData.latitude = domain.getData().getGps().latitude()
                );

        sensorDataToUpdate.stream()
                .filter(e -> e.has(SensorDataRecordLabel.GPS_LONGITUDE))
                .findFirst()
                .ifPresentOrElse(
                        e -> gpsData.longitude = Double.parseDouble(e.content()),
                        () -> gpsData.longitude = domain.getData().getGps().longitude()
                );
        sensorData.gps = gpsData;

        var deviceRecordDTO = new DeviceRecordDTO(domain.getRecords()
                .entries()
                .stream()
                .filter(e -> e instanceof BasicRecordEntry)
                .map(e -> (BasicRecordEntry) e)
                .map(e -> new DeviceRecordBasicEntryDTO(e.label(), e.content()))
                .collect(Collectors.toSet()));

        var deviceInfo = new DeviceInformationWithRecordsDTO(device.getId(), device.getName(), deviceRecordDTO);

        return new ProcessedSensorDataWithRecordsDTO(dataId, deviceInfo, reportedAt, sensorData);
    }
}

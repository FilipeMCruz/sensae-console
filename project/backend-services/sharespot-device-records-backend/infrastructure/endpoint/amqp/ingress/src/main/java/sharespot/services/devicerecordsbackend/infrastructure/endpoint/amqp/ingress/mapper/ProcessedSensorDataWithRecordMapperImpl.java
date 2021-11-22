package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordDTO;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.BasicRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.SensorDataRecordEntry;
import sharespot.services.devicerecordsbackend.domain.model.records.SensorDataRecordLabel;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorDataWithRecord;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model.*;

import java.util.stream.Collectors;

@Service
public class ProcessedSensorDataWithRecordMapperImpl implements ProcessedSensorDataWithRecordMapper {

    @Override
    public ProcessedSensorDataWithRecordDTO domainToDto(ProcessedSensorDataWithRecord domain) {
        var dataId = domain.getDataId();
        var deviceId = domain.getDeviceId();
        var reportedAt = domain.getReportedAt();

        var sensorDataToUpdate = domain.getRecords()
                .entries()
                .stream()
                .filter(e -> e instanceof SensorDataRecordEntry)
                .map(e -> (SensorDataRecordEntry) e)
                .collect(Collectors.toSet());

        var sensorData = new SensorDataDetailsDTOImpl();
        var gpsData = new GPSDataDetailsDTOImpl();

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

        var deviceRecordDTO = new DeviceRecordDTOImpl(domain.getRecords()
                .entries()
                .stream()
                .filter(e -> e instanceof BasicRecordEntry)
                .map(e -> (BasicRecordEntry) e)
                .map(e -> new DeviceRecordBasicEntryDTOImpl(e.label(), e.content()))
                .collect(Collectors.toSet()));

        return new ProcessedSensorDataWithRecordDTOImpl(dataId, deviceId, reportedAt, sensorData, deviceRecordDTO);
    }
}

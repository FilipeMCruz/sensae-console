package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataMapper;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensor;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorData;
import sharespot.services.devicerecordsbackend.domain.model.sensors.SensorDataDetails;

@Service
public class ProcessedSensorDataMapperImpl implements ProcessedSensorDataMapper {
    @Override
    public ProcessedSensorData dtoToDomain(ProcessedSensorDataDTO trueDto) {
        var data = new SensorDataDetails.GPS(trueDto.data.gps.latitude, trueDto.data.gps.longitude);
        var device = new ProcessedSensor(trueDto.device.name, trueDto.device.id);
        return new ProcessedSensorData(trueDto.dataId, device, trueDto.reportedAt, new SensorDataDetails(data));
    }
}

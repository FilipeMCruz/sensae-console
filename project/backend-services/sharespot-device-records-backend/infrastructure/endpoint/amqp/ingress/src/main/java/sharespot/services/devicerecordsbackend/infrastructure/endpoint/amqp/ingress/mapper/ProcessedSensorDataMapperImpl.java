package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataMapper;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorData;
import sharespot.services.devicerecordsbackend.domain.model.sensors.SensorDataDetails;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model.ProcessedSensorDataDTOImpl;

@Service
public class ProcessedSensorDataMapperImpl implements ProcessedSensorDataMapper {
    @Override
    public ProcessedSensorData dtoToDomain(ProcessedSensorDataDTO dto) {
        var trueDto = (ProcessedSensorDataDTOImpl) dto;
        var data = new SensorDataDetails.GPS(trueDto.data.gps.latitude, trueDto.data.gps.longitude);
        return new ProcessedSensorData(trueDto.dataId, trueDto.deviceId, trueDto.reportedAt, new SensorDataDetails(data));
    }
}

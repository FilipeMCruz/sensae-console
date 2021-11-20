package sharespot.services.lgt92gpsdataprocessor.infrastructure.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.lgt92gpsdataprocessor.application.InSensorDataDTO;
import sharespot.services.lgt92gpsdataprocessor.application.OutSensorDataDTO;
import sharespot.services.lgt92gpsdataprocessor.application.SensorDataMapper;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.ProcessedSensorData;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.SensorDataDetails;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpingress.model.SensorData;

@Service
public class SensorDataMapperImpl implements SensorDataMapper {

    @Override
    public OutSensorDataDTO inToOut(InSensorDataDTO inDto) {
        SensorData data = (SensorData) inDto;
        return new ProcessedSensorData(
                data.uuid,
                data.id,
                data.reportedAt,
                //TODO: add new sensor types
                new SensorDataDetails(data.decoded.payload.latitude, data.decoded.payload.longitude)
        );
    }
}

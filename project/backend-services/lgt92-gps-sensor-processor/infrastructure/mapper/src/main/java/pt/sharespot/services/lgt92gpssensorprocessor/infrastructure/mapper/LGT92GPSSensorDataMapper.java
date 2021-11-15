package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.services.lgt92gpssensorprocessor.application.InSensorDataDTO;
import pt.sharespot.services.lgt92gpssensorprocessor.application.OutSensorDataDTO;
import pt.sharespot.services.lgt92gpssensorprocessor.application.SensorDataMapper;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.model.ProcessedSensorData;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.model.GPSDataDetails;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.SensorData;

@Service
public class LGT92GPSSensorDataMapper implements SensorDataMapper {

    @Override
    public OutSensorDataDTO inToOut(InSensorDataDTO inDto) {
        SensorData data = (SensorData) inDto;
        return new ProcessedSensorData(
                data.uuid,
                data.deviceId,
                data.reportDate,
                new GPSDataDetails(data.decoded.payload.latitude, data.decoded.payload.longitude)
        );
    }
}

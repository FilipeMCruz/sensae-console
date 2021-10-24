package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorprocessor.application.InSensorDataDTO;
import pt.sharespot.services.lgt92gpssensorprocessor.application.OutSensorDataDTO;
import pt.sharespot.services.lgt92gpssensorprocessor.application.SensorDataMapper;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.model.gps.GPSData;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.model.gps.GPSDataDetails;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.lgt92sensor.LGT92SensorData;

@Component
public class LGT92GPSSensorDataMapper implements SensorDataMapper {

    @Override
    public OutSensorDataDTO inToOut(InSensorDataDTO inDto) {
        LGT92SensorData in = (LGT92SensorData) inDto;
        return new GPSData(in.uuid,
                in.deviceId,
                in.reportDate,
                new GPSDataDetails(in.decoded.payload.latitude, in.decoded.payload.longitude)
        );
    }
}

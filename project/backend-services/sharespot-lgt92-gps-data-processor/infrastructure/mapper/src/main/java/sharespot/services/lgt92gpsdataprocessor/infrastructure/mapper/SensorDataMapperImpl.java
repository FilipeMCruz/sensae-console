package sharespot.services.lgt92gpsdataprocessor.infrastructure.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.lgt92gpsdataprocessor.application.InSensorDataDTO;
import sharespot.services.lgt92gpsdataprocessor.application.OutSensorDataDTO;
import sharespot.services.lgt92gpsdataprocessor.application.SensorDataMapper;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.GPSDataDetailsDTOImpl;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.ProcessedSensorDataDTOImpl;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.SensorDataDetailsDTOImpl;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpingress.model.SensorData;

@Service
public class SensorDataMapperImpl implements SensorDataMapper {

    @Override
    public OutSensorDataDTO inToOut(InSensorDataDTO inDto) {
        SensorData data = (SensorData) inDto;
        var dataDTO = new SensorDataDetailsDTOImpl();
        //TODO: add new sensor types
        dataDTO.gps = new GPSDataDetailsDTOImpl(data.decoded.payload.latitude, data.decoded.payload.longitude);
        return new ProcessedSensorDataDTOImpl(
                data.uuid,
                data.id,
                data.reportedAt,
                dataDTO
        );
    }
}

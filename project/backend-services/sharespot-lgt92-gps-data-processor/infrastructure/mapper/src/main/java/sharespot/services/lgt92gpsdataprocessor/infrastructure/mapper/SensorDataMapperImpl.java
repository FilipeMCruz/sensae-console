package sharespot.services.lgt92gpsdataprocessor.infrastructure.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sharespot.services.lgt92gpsdataprocessor.application.OutSensorDataDTO;
import sharespot.services.lgt92gpsdataprocessor.application.SensorDataMapper;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.GPSDataDetailsDTOImpl;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.ProcessedSensorDTOImpl;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.ProcessedSensorDataDTOImpl;
import sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model.SensorDataDetailsDTOImpl;

import java.util.Optional;

@Service
public class SensorDataMapperImpl implements SensorDataMapper {

    Logger logger = LoggerFactory.getLogger(SensorDataMapperImpl.class);

    @Override
    public Optional<OutSensorDataDTO> inToOut(JsonNode inDto) {
        try {
            var dataDTO = new SensorDataDetailsDTOImpl();
            //TODO: add new sensor types
            dataDTO.gps = new GPSDataDetailsDTOImpl(
                    inDto.path("decoded").path("payload").path("Latitude").asDouble(),
                    inDto.path("decoded").path("payload").path("Longitude").asDouble()
            );

            var sensor = new ProcessedSensorDTOImpl(
                    inDto.path("name").asText(),
                    inDto.path("id").asText()
            );

            var outSensor = new ProcessedSensorDataDTOImpl(
                    inDto.path("uuid").asText(),
                    sensor,
                    inDto.path("reported_at").asLong(),
                    dataDTO
            );

            return Optional.of(outSensor);
        } catch (Exception e) {
            logger.error("Error processing sensor data: " + inDto.toPrettyString(), e.fillInStackTrace());
            return Optional.empty();
        }
    }
}

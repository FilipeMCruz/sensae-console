package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.device.DeviceInformationDTO;

import java.util.Optional;
import java.util.UUID;

@Service
public class SensorDataMapper {

    Logger logger = LoggerFactory.getLogger(SensorDataMapper.class);

    public Optional<SensorDataDTO> inToOut(JsonNode inDto) {
        try {
            var dataDTO = new SensorDataDetailsDTO();
            //TODO: add new sensor types
            dataDTO.gps = new GPSDataDTO(
                    inDto.path("decoded").path("payload").path("Latitude").asDouble(),
                    inDto.path("decoded").path("payload").path("Longitude").asDouble()
            );

            var sensor = new DeviceInformationDTO(
                    UUID.fromString(inDto.path("id").asText()),
                    inDto.path("name").asText()
            );

            var outSensor = new ProcessedSensorDataDTO(
                    UUID.fromString(inDto.path("uuid").asText()),
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

package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorData;
import pt.sharespot.iot.core.sensor.SensorData;
import pt.sharespot.iot.core.sensor.data.GPSData;
import pt.sharespot.iot.core.sensor.data.SensorDataDetails;
import pt.sharespot.iot.core.sensor.device.DeviceInformation;

import java.util.Optional;

@Service
public class SensorDataMapper {

    Logger logger = LoggerFactory.getLogger(SensorDataMapper.class);

    public Optional<SensorData> inToOut(JsonNode inDto) {
        try {
            var dataDTO = new SensorDataDetails();
            //TODO: add new sensor types
            dataDTO.gps = new GPSData(
                    inDto.path("decoded").path("payload").path("Latitude").asDouble(),
                    inDto.path("decoded").path("payload").path("Longitude").asDouble()
            );

            var sensor = new DeviceInformation(
                    inDto.path("name").asText(),
                    inDto.path("id").asText()
            );

            var outSensor = new ProcessedSensorData(
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

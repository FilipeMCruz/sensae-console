package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public interface SensorDataMapper {

    Optional<OutSensorDataDTO> inToOut(JsonNode inDto);
}

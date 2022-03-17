package sharespot.services.datadecoder.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domainservices.DataDecoderCache;

import java.util.Optional;

@Service
public class SensorDataMapper {

    private final DataDecoderCache cache;

    public SensorDataMapper(DataDecoderCache cache, ObjectMapper mapper) {
        this.cache = cache;
    }

    public Optional<SensorDataDTO> inToOut(JsonNode inDto, SensorTypeId typeId) {
        return Optional.empty();
    }
}

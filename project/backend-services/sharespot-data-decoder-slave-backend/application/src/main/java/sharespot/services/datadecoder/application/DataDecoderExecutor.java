package sharespot.services.datadecoder.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domainservices.DataDecoderCache;

import java.util.Map;
import java.util.Optional;

@Service
public class DataDecoderExecutor {

    private final ObjectMapper mapper;
    private final DataDecoderCache cache;

    public DataDecoderExecutor(DataDecoderCache cache) {
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.cache = cache;
    }

    public Optional<SensorDataDTO> decodeData(JsonNode input, SensorTypeId scriptId) {
        var object = mapper.convertValue(input, new TypeReference<Map<String, Object>>() {
        });
        return cache.findByDeviceId(scriptId).map(s -> s.execute(ProxyObject.fromMap(object)).as(Map.class))
                .map(r -> mapper.convertValue(r, ProcessedSensorDataDTO.class));
    }
}

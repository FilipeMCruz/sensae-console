package sharespot.services.datadecoder.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domainservices.DataDecoderCache;

import java.io.IOException;
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

    public Optional<ProcessedSensorDataDTO> decodeData(JsonNode input, SensorTypeId scriptId) {
        var byDeviceId = cache.findByDeviceId(scriptId);
        if (byDeviceId.isEmpty()) return Optional.empty();

        try (var context = Context.create()) {
            context.eval(Source.newBuilder("js", byDeviceId.get().getScript().getValue(), scriptId.getValue()).build());
            var member = Optional.of(context.getBindings("js").getMember("convert"));

            var object = mapper.convertValue(input, new TypeReference<Map<String, Object>>() {
            });
            return member.map(s -> s.execute(ProxyObject.fromMap(object)).as(Map.class))
                    .map(r -> mapper.convertValue(r, ProcessedSensorDataDTO.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

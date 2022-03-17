package sharespot.services.datadecoder.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import sharespot.services.datadecoder.domain.SensorTypeId;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
public class DataDecoderExecutor {

    private final Cache<SensorTypeId, Value> cache;

    private final ObjectMapper mapper;

    public DataDecoderExecutor() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(10)
                .build();
    }

    public void addScript(SensorTypeId id, String script) {
        var oldScript = cache.getIfPresent(id);
        if (oldScript != null) {
            oldScript.getContext().close();
        }

        try (var context = Context.create()) {
            context.eval(Source.newBuilder("js", script, id.getValue()).build());
            Value convert = context.getBindings("js").getMember("convert");
            cache.put(id, convert);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasScript(SensorTypeId id) {
        return cache.getIfPresent(id) != null;
    }

    public Optional<SensorDataDTO> decodeData(JsonNode input, SensorTypeId scriptId) {
        var script = cache.getIfPresent(scriptId);
        if (script == null) {
            return Optional.empty();
        }
        var object = mapper.convertValue(input, new TypeReference<Map<String, Object>>() {
        });
        var result = script.execute(ProxyObject.fromMap(object)).as(Map.class);
        return Optional.of(mapper.convertValue(result, ProcessedSensorDataDTO.class));
    }

    public void clean() {
        cache.asMap().forEach((key, value) -> value.getContext().close());
        cache.cleanUp();
    }
}

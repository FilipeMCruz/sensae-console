package pt.sensae.services.data.decoder.flow.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.proxy.ProxyHashMap;
import org.graalvm.polyglot.proxy.ProxyObject;
import pt.sensae.services.data.decoder.flow.domain.DataDecoderRepository;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class DataDecoderExecutor {

    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Inject
    DataDecoderRepository repository;

    public Optional<DataUnitDTO> decodeData(JsonNode input, SensorTypeId scriptId) {
        var scriptSource = repository.findById(scriptId);
        if (scriptSource.isEmpty()) return Optional.empty();

        try (var context = Context.create()) {
            context.eval(Source.newBuilder("js", scriptSource.get().script().value(), scriptId.getValue()).build());
            var object = mapper.convertValue(input, new TypeReference<Map<String, Object>>() {
            });

            return Optional.of(context.getBindings("js").getMember("convert"))
                    .map(s -> s.execute(ProxyObject.fromMap(object)).as(Map.class))
                    .map(r -> mapper.convertValue(r, DataUnitDTO.class));
        } catch (IOException | PolyglotException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

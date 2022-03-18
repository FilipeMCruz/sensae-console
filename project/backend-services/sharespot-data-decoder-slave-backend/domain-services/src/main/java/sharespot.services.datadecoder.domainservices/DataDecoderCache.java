package sharespot.services.datadecoder.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.domain.SensorDataDecodersRepository;
import sharespot.services.datadecoder.domain.SensorTypeId;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Service
public class DataDecoderCache {

    private final Cache<SensorTypeId, Optional<Value>> cache;

    private final SensorDataDecodersRepository repository;

    public DataDecoderCache(SensorDataDecodersRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(10)
                .build();
    }

    public Optional<Value> update(SensorTypeId id) {
        var deviceType = repository.findByDeviceType(id).flatMap(d -> addScript(id, d.getScript().getValue()));
        cache.put(id, deviceType);
        return deviceType;
    }

    public void clean() {
        cache.asMap().forEach((key, value) -> value.ifPresent(v -> v.getContext().close()));
        cache.cleanUp();
    }

    private Optional<Value> addScript(SensorTypeId id, String script) {
        var oldScript = findByDeviceId(id);
        oldScript.ifPresent(o -> o.getContext().close());

        try (var context = Context.create()) {
            context.eval(Source.newBuilder("js", script, id.getValue()).build());
            return Optional.of(context.getBindings("js").getMember("convert"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Value> findByDeviceId(SensorTypeId id) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id));
    }
}

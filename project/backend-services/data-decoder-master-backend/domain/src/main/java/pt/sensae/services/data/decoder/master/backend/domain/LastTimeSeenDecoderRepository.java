package pt.sensae.services.data.decoder.master.backend.domain;

import java.time.Instant;
import java.util.Optional;

public interface LastTimeSeenDecoderRepository {

    void update(SensorTypeId id);

    Optional<Instant> find(SensorTypeId id);
}

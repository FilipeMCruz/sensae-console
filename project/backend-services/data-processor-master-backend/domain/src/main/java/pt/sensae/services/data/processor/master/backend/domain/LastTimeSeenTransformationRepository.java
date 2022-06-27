package pt.sensae.services.data.processor.master.backend.domain;

import java.time.Instant;
import java.util.Optional;

public interface LastTimeSeenTransformationRepository {

    void update(SensorTypeId id);

    Optional<Instant> find(SensorTypeId id);
}

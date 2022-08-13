package pt.sensae.services.data.decoder.flow.domain;

import java.util.Optional;

public interface DataDecoderRepository {

    Optional<DataDecoder> findById(SensorTypeId id);

    void update(DataDecoder info);

    void delete(SensorTypeId id);
}

package pt.sensae.services.data.decoder.master.backend.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface DataDecodersRepository {

    DataDecoder save(DataDecoder records);

    Stream<DataDecoder> findAll();

    SensorTypeId delete(SensorTypeId id);

    Optional<DataDecoder> findById(SensorTypeId id);
}

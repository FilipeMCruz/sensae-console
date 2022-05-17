package sharespot.services.data.decoder.master.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface SensorDataDecodersRepository {

    DataDecoder save(DataDecoder records);

    Stream<DataDecoder> findAll();

    SensorTypeId delete(SensorTypeId id);

    Optional<DataDecoder> findById(SensorTypeId id);
}

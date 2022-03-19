package sharespot.services.datadecodermaster.domain;

import java.util.stream.Stream;

public interface SensorDataDecodersRepository {

    DataDecoder save(DataDecoder records);

    Stream<DataDecoder> findAll();

    SensorTypeId delete(SensorTypeId id);
}

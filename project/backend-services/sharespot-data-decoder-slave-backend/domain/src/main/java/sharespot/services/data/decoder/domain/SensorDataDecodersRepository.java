package sharespot.services.data.decoder.domain;

import java.util.Optional;

public interface SensorDataDecodersRepository {

    Optional<DataDecoder> findByDeviceType(SensorTypeId id);
}

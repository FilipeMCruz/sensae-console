package sharespot.services.datadecoder.domain;

import java.util.Optional;

public interface SensorDataDecodersRepository {

    Optional<DataDecoder> findByDeviceType(SensorTypeId id);
}

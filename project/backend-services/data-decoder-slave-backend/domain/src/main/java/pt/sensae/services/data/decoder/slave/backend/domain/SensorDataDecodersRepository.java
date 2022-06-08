package pt.sensae.services.data.decoder.slave.backend.domain;

import java.util.Optional;

public interface SensorDataDecodersRepository {

    Optional<DataDecoder> findByDeviceType(SensorTypeId id);
}

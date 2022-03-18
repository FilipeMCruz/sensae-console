package sharespot.services.datadecoder.infrastructure.persistence.filesystem.repository;

import org.springframework.data.repository.CrudRepository;
import sharespot.services.datadecoder.infrastructure.persistence.filesystem.model.DataDecoderPostgres;

import java.util.Optional;

public interface SensorDataDecoderRepositoryPostgres extends CrudRepository<DataDecoderPostgres, Long> {

    Optional<DataDecoderPostgres> findByDeviceType(String deviceType);
}

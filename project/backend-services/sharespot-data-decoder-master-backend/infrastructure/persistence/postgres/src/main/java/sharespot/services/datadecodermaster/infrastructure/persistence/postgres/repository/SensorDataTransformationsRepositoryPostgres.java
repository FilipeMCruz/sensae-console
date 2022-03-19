package sharespot.services.datadecodermaster.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sharespot.services.datadecodermaster.infrastructure.persistence.postgres.model.DataDecoderPostgres;

import java.util.Optional;

@Repository
public interface SensorDataTransformationsRepositoryPostgres extends CrudRepository<DataDecoderPostgres, Long> {

    Optional<DataDecoderPostgres> findByDeviceType(String deviceType);

    void deleteByDeviceType(String deviceType);
}

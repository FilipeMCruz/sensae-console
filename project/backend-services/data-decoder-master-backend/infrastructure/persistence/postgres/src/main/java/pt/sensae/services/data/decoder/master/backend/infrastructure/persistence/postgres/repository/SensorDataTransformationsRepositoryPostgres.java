package pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.model.DataDecoderPostgres;

import java.util.Optional;

@Repository
public interface SensorDataTransformationsRepositoryPostgres extends CrudRepository<DataDecoderPostgres, Long> {

    Optional<DataDecoderPostgres> findByDeviceType(String deviceType);

    void deleteByDeviceType(String deviceType);
}

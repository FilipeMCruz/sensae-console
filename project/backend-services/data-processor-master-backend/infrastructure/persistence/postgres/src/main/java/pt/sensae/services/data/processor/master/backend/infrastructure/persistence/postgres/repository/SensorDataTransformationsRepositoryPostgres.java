package pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres.model.DataTransformationPostgres;

import java.util.Optional;

@Repository
public interface SensorDataTransformationsRepositoryPostgres extends CrudRepository<DataTransformationPostgres, Long> {

    Optional<DataTransformationPostgres> findByDeviceType(String deviceType);
    
    void deleteByDeviceType(String deviceType);
}

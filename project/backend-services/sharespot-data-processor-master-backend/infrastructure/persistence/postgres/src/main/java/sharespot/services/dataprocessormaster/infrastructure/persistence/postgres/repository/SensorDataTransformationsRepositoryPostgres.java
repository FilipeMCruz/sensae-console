package sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.DataTransformationPostgres;

import java.util.Optional;

@Repository
public interface SensorDataTransformationsRepositoryPostgres extends CrudRepository<DataTransformationPostgres, Long> {

    Optional<DataTransformationPostgres> findByDeviceType(String deviceType);
    
    void deleteByDeviceType(String deviceType);
}

package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.DevicePostgres;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DevicePostgres, Long> {

    Stream<DevicePostgres> getAllByDeviceIdIsIn(List<String> deviceIds);
}

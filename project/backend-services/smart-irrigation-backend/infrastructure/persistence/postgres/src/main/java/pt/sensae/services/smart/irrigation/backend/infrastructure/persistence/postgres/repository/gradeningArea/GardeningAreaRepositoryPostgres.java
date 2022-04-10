package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaPostgres;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface GardeningAreaRepositoryPostgres extends CrudRepository<GardeningAreaPostgres, Long> {

    Optional<GardeningAreaPostgres> findByAreaId(String areaId);

    Stream<GardeningAreaPostgres> findByAreaIdIn(List<String> areaId);

}

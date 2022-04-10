package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.AreaBoundariesPostgres;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AreaBoundariesRepositoryPostgres extends CrudRepository<AreaBoundariesPostgres, Long> {

    Stream<AreaBoundariesPostgres> findAllByAreaId(String areaId);

    Stream<AreaBoundariesPostgres> findAllByAreaIdIn(List<String> areaIds);
}

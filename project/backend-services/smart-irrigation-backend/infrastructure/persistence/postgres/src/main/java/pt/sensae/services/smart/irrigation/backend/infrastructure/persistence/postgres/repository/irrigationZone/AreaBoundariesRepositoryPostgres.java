package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.irrigationZone;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone.AreaBoundariesPostgres;

import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface AreaBoundariesRepositoryPostgres extends CrudRepository<AreaBoundariesPostgres, Long> {


    @Modifying
    @Query("delete from gardening_area_boundary a where a.area_id = :areaId")
    void deleteAllByAreaId(@Param("areaId") String areaId);

    Stream<AreaBoundariesPostgres> findAllByAreaIdIn(Set<String> areaIds);
}

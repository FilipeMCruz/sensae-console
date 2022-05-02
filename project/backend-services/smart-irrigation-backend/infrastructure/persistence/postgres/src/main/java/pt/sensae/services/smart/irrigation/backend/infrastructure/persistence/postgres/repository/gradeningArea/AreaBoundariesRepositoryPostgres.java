package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.AreaBoundariesPostgres;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AreaBoundariesRepositoryPostgres extends CrudRepository<AreaBoundariesPostgres, Long> {


    @Modifying
    @Query("delete from gardening_area_boundary a where a.area_id = :areaId")
    void deleteAllByAreaId(@Param("areaId") String areaId);

    Stream<AreaBoundariesPostgres> findAllByAreaId(String areaId);

    Stream<AreaBoundariesPostgres> findAllByAreaIdIn(List<String> areaIds);
}

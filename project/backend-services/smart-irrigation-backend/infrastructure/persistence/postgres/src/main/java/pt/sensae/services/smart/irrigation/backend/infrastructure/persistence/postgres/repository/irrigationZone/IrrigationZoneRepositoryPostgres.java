package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.irrigationZone;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone.IrrigationZonePostgres;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface IrrigationZoneRepositoryPostgres extends CrudRepository<IrrigationZonePostgres, Long> {

    Optional<IrrigationZonePostgres> findByDeletedFalseAndAreaId(String areaId);

    Optional<IrrigationZonePostgres> findByAreaId(String areaId);

    Stream<IrrigationZonePostgres> findAllByDeletedFalseAndAreaIdIn(Set<String> areaId);

    Stream<IrrigationZonePostgres> findAllByDeletedFalse();

}

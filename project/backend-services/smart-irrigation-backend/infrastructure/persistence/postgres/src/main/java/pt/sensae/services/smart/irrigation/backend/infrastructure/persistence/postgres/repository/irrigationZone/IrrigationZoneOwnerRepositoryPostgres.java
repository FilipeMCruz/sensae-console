package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.irrigationZone;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone.IrrigationZoneOwnerPostgres;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface IrrigationZoneOwnerRepositoryPostgres extends CrudRepository<IrrigationZoneOwnerPostgres, Long> {

    Stream<IrrigationZoneOwnerPostgres> findAllByDomainIdIn(List<String> domainIds);

    Stream<IrrigationZoneOwnerPostgres> findAllByAreaIdIn(Set<String> areaIds);
}

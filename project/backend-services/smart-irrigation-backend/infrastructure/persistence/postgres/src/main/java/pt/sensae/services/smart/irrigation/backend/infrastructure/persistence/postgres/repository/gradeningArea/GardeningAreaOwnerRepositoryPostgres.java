package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.gradeningArea;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaOwnerPostgres;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaPostgres;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface GardeningAreaOwnerRepositoryPostgres extends CrudRepository<GardeningAreaOwnerPostgres, Long> {

    Stream<GardeningAreaOwnerPostgres> findAllByDomainIdIn(List<String> domainIds);

    Stream<GardeningAreaOwnerPostgres> findAllByAreaIdIn(Set<String> areaIds);
}

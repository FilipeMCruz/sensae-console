package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;

import java.util.Optional;
import java.util.stream.Stream;

public interface GardenRepository {

    Stream<GardeningArea> fetchMultiple(Stream<GardeningAreaId> id);

    Stream<GardeningArea> fetchAll(Stream<DomainId> owners);

    GardeningArea save(GardeningArea garden);

    void delete(GardeningAreaId id);
}

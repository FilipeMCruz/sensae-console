package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import java.util.Optional;
import java.util.stream.Stream;

public interface GardenRepository {
    
    Optional<GardeningArea> fetchById(GardeningAreaId id);

    Stream<GardeningArea> fetchMultiple(Stream<GardeningAreaId> id);

    Stream<GardeningArea> fetchAll();

    GardeningArea save(GardeningArea garden);

}

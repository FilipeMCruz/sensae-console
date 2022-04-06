package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import java.util.Optional;
import java.util.stream.Stream;

public interface GardenRepository {

    Optional<GardeningArea> fetchById(GardenId id);

    Stream<GardeningArea> fetchMultiple(Stream<GardenId> id);

    GardeningArea save(GardeningArea garden);

}

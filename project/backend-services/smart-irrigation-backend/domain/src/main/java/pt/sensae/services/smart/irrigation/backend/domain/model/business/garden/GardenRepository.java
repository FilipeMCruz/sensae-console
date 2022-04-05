package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import java.util.Optional;
import java.util.stream.Stream;

public interface GardenRepository {

    Optional<Garden> fetchById(GardenId id);

    Stream<Garden> fetchMultiple(Stream<GardenId> id);

    Garden save(Garden garden);

}

package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class GardenRepositoryImpl implements GardenRepository {

    @Override
    public Optional<GardeningArea> fetchById(GardenId id) {
        return Optional.empty();
    }

    @Override
    public Stream<GardeningArea> fetchMultiple(Stream<GardenId> id) {
        return Stream.empty();
    }

    @Override
    public GardeningArea save(GardeningArea garden) {
        return garden;
    }
}

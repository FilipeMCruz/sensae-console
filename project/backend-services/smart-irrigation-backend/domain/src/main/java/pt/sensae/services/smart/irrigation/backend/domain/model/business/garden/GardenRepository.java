package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;

import java.util.stream.Stream;

public interface GardenRepository {

    Stream<GardeningArea> fetchMultiple(Stream<GardeningAreaId> id);

    Stream<GardeningArea> fetchAll(Ownership owners);

    GardeningArea save(GardeningArea garden);

    void delete(GardeningAreaId id);
}

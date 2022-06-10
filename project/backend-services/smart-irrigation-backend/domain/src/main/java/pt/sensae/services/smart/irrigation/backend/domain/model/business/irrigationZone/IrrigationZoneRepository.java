package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;

import java.util.stream.Stream;

public interface IrrigationZoneRepository {

    Stream<IrrigationZone> fetchMultiple(Stream<IrrigationZoneId> id);

    Stream<IrrigationZone> fetchAll(Ownership owners);

    IrrigationZone save(IrrigationZone irrigationZone);

    void delete(IrrigationZoneId id);
}

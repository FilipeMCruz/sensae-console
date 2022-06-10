package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.irrigationZone.IrrigationZonePostgres;

import java.util.UUID;

public class IrrigationZoneMapper {

    public static IrrigationZonePostgres modelToDao(IrrigationZone model) {
        var dao = new IrrigationZonePostgres();
        dao.areaId = model.id().value().toString();
        dao.areaName = model.name().value();
        dao.deleted = false;
        return dao;
    }

    public static IrrigationZone daoToModel(IrrigationZonePostgres dao, Area area, Owners owners) {
        var id = IrrigationZoneId.of(UUID.fromString(dao.areaId));
        var name = IrrigationZoneName.of(dao.areaName);

        return new IrrigationZone(id, name, area, owners);
    }
}

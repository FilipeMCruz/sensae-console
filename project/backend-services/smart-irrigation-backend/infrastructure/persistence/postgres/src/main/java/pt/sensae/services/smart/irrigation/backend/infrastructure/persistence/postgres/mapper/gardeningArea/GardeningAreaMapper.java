package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Area;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardenName;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaPostgres;

import java.util.UUID;

public class GardeningAreaMapper {

    public static GardeningAreaPostgres modelToDao(GardeningArea model) {
        var dao = new GardeningAreaPostgres();
        dao.areaId = model.id().value().toString();
        dao.areaName = model.name().value();
        return dao;
    }

    public static GardeningArea daoToModel(GardeningAreaPostgres dao, Area area) {
        var id = GardeningAreaId.of(UUID.fromString(dao.areaId));
        var name = GardenName.of(dao.areaName);

        return new GardeningArea(id, name, area);
    }
}

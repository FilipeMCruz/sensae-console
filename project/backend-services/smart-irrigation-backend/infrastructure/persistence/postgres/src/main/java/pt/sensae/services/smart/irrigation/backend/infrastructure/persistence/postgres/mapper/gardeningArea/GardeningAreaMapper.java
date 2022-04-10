package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.GardeningAreaPostgres;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class GardeningAreaMapper {

    public static GardeningAreaPostgres modelToDao(GardeningArea model) {
        var dao = new GardeningAreaPostgres();
        dao.irrigationSystem = model.valves().devices().stream().map(id -> id.value().toString()).collect(Collectors.joining(",", "{", "}"));
        dao.areaId = model.id().value().toString();
        dao.areaName = model.name().value();
        dao.areaType = model.type().name();
        return dao;
    }

    public static GardeningArea daoToModel(GardeningAreaPostgres dao, Area area) {
        var collect = Arrays.stream(dao.irrigationSystem.substring(1, dao.irrigationSystem.length() - 2).split(",")).map(UUID::fromString).map(DeviceId::of);
        var valves = new IrrigationSystem(collect.collect(Collectors.toSet()));

        var id = GardeningAreaId.of(UUID.fromString(dao.areaId));
        var name = GardenName.of(dao.areaName);
        var type = GardeningAreaType.STOVE;
        if (dao.areaType.equals(GardeningAreaType.PARK.name())) {
            type = GardeningAreaType.PARK;
        }
        return new GardeningArea(id, name, type, area, valves);
    }
}

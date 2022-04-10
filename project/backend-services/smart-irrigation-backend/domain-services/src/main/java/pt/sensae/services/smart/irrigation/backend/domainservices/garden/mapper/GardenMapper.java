package pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.*;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.BoundaryCommandDetails;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.CreateGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class GardenMapper {

    public static GardeningArea toModel(UpdateGardeningAreaCommand updateCommand) {
        var of = GardeningAreaId.of(updateCommand.id);
        return getGardeningArea(updateCommand.valvesIds, updateCommand.area, updateCommand.type, of, updateCommand.name);
    }

    public static GardeningArea toModel(CreateGardeningAreaCommand updateCommand) {
        var of = GardeningAreaId.create();
        return getGardeningArea(updateCommand.valvesIds, updateCommand.area, updateCommand.type, of, updateCommand.name);
    }

    private static GardeningArea getGardeningArea(List<UUID> valves, List<BoundaryCommandDetails> boundaryCommands, String typeCommand, GardeningAreaId of, String name) {
        var irrigationSystem = new IrrigationSystem(valves.stream()
                .map(DeviceId::of)
                .collect(Collectors.toSet()));

        var area = new Area(boundaryCommands.stream().map(b -> {
            var gps = new GPSPoint(b.latitude, b.longitude, b.altitude);
            return new BoundaryPoint(b.position, gps);
        }).collect(Collectors.toSet()));

        var type = GardeningAreaType.STOVE;
        if (Objects.equals(typeCommand, "park")) {
            type = GardeningAreaType.PARK;
        }

        return new GardeningArea(of, GardenName.of(name), type, area, irrigationSystem);
    }
}

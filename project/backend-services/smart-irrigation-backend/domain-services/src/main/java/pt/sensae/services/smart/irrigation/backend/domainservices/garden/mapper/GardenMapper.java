package pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.*;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.BoundaryCommandDetails;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.CreateGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

import java.util.List;
import java.util.stream.Collectors;

public class GardenMapper {

    public static GardeningArea toModel(UpdateGardeningAreaCommand updateCommand) {
        var of = GardeningAreaId.of(updateCommand.id);
        return getGardeningArea(updateCommand.area, of, updateCommand.name);
    }

    public static GardeningArea toModel(CreateGardeningAreaCommand createCommand) {
        var of = GardeningAreaId.create();
        return getGardeningArea(createCommand.area, of, createCommand.name);
    }

    private static GardeningArea getGardeningArea(List<BoundaryCommandDetails> boundaryCommands, GardeningAreaId of, String name) {
        var area = new Area(boundaryCommands.stream().map(b -> {
            var gps = new GPSPoint(b.latitude, b.longitude, b.altitude);
            return new BoundaryPoint(b.position, gps);
        }).collect(Collectors.toSet()));

        return new GardeningArea(of, GardenName.of(name), area, Owners.empty());
    }
}

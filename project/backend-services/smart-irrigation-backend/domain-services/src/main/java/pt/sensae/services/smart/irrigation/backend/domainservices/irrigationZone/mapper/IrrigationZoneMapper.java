package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.*;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.BoundaryCommandDetails;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.CreateIrrigationZoneCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.UpdateIrrigationZoneCommand;

import java.util.List;
import java.util.stream.Collectors;

public class IrrigationZoneMapper {

    public static IrrigationZone toModel(UpdateIrrigationZoneCommand updateCommand) {
        var of = IrrigationZoneId.of(updateCommand.id);
        return getGardeningArea(updateCommand.area, of, updateCommand.name);
    }

    public static IrrigationZone toModel(CreateIrrigationZoneCommand createCommand) {
        var of = IrrigationZoneId.create();
        return getGardeningArea(createCommand.area, of, createCommand.name);
    }

    private static IrrigationZone getGardeningArea(List<BoundaryCommandDetails> boundaryCommands, IrrigationZoneId of, String name) {
        var area = new Area(boundaryCommands.stream().map(b -> {
            var gps = new GPSPoint(b.latitude, b.longitude, b.altitude);
            return new BoundaryPoint(b.position, gps);
        }).collect(Collectors.toSet()));

        return new IrrigationZone(of, IrrigationZoneName.of(name), area, Owners.empty());
    }
}

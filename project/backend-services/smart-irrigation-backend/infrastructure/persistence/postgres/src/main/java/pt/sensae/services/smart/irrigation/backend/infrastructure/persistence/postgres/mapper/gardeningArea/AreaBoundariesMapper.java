package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.mapper.gardeningArea;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Area;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.BoundaryPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.gardeningArea.AreaBoundariesPostgres;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AreaBoundariesMapper {

    public static Stream<AreaBoundariesPostgres> modelToDao(Area area, GardeningAreaId areaId) {
        return area.boundaries().stream().map(b -> {
            var altitude = b.point().altitude() != null ? b.point().altitude().toString() : null;

            var point = new AreaBoundariesPostgres();
            point.areaId = areaId.value().toString();
            point.position = b.position();
            point.longitude = b.point().longitude().toString();
            point.latitude = b.point().latitude().toString();
            point.altitude = altitude;
            return point;
        });
    }

    public static Area daoToModel(Stream<AreaBoundariesPostgres> points) {
        var collect = points.map(dao -> {
            var aFloat = dao.altitude != null ? Float.parseFloat(dao.altitude) : null;

            var gpsPoint = new GPSPoint(Float.parseFloat(dao.latitude), Float.parseFloat(dao.longitude), aFloat);
            return new BoundaryPoint(dao.position, gpsPoint);
        }).collect(Collectors.toSet());
        return new Area(collect);
    }
}

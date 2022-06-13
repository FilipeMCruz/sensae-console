package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.AreaBoundaryDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.IrrigationZoneDTOImpl;

@Service
public class IrrigationZoneDTOMapperImpl implements IrrigationZoneDTOMapper {

    @Override
    public IrrigationZoneDTO toDto(IrrigationZone model) {
        var dto = new IrrigationZoneDTOImpl();
        dto.id = model.id().value().toString();
        dto.name = model.name().value();
        dto.area = model.area().boundaries().stream().map(b -> {
            var out = new AreaBoundaryDTOImpl();
            out.altitude = String.valueOf(b.point().altitude());
            out.longitude = String.valueOf(b.point().longitude());
            out.latitude = String.valueOf(b.point().latitude());
            out.position = b.position();
            return out;
        }).toList();
        return dto;
    }
}

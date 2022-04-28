package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.AreaBoundaryDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.GardeningAreaDTOImpl;

@Service
public class GardeningAreaDTOMapperImpl implements GardeningAreaDTOMapper {

    @Override
    public GardeningAreaDTO toDto(GardeningArea model) {
        var dto = new GardeningAreaDTOImpl();
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

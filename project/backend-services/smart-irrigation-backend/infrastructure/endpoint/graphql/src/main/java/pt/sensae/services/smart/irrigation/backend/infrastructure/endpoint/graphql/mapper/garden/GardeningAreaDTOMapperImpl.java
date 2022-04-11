package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.AreaBoundaryDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.GardeningAreaDTOImpl;

@Service
public class GardeningAreaDTOMapperImpl implements GardeningAreaDTOMapper {

    @Override
    public GardeningAreaDTO toDto(GardeningArea model) {
        var dto = new GardeningAreaDTOImpl();
        dto.id = model.id().value().toString();
        dto.name = model.name().value();
        dto.area = model.area().boundaries().stream().map(b -> {
            var out = new AreaBoundaryDTO();
            out.altitude = b.point().altitude();
            out.longitude = b.point().longitude();
            out.latitude = b.point().latitude();
            out.position = b.position();
            return out;
        }).toList();
        return dto;
    }
}
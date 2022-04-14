package pt.sensae.services.smart.irrigation.backend.application.mapper.garden;

import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;

public interface GardeningAreaDTOMapper {

    GardeningAreaDTO toDto(GardeningArea model);
}

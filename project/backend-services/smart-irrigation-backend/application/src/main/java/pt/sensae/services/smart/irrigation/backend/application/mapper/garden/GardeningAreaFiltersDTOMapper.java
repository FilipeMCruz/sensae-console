package pt.sensae.services.smart.irrigation.backend.application.mapper.garden;

import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaFiltersDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.GardeningAreaFilters;

public interface GardeningAreaFiltersDTOMapper {

    GardeningAreaFilters toCommand(GardeningAreaFiltersDTO dto);
}

package pt.sensae.services.smart.irrigation.backend.application.mapper.garden;

import pt.sensae.services.smart.irrigation.backend.application.model.garden.CreateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.DeleteGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.UpdateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.CreateGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.DeleteGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

public interface GardeningAreaCommandDTOMapper {

    UpdateGardeningAreaCommand toCommand(UpdateGardeningAreaCommandDTO dto);

    CreateGardeningAreaCommand toCommand(CreateGardeningAreaCommandDTO dto);

    DeleteGardeningAreaCommand toCommand(DeleteGardeningAreaCommandDTO dto);
}

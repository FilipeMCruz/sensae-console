package pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.CreateIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.DeleteIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.UpdateIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.CreateIrrigationZoneCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.DeleteIrrigationZoneCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.UpdateIrrigationZoneCommand;

public interface IrrigationZoneCommandDTOMapper {

    UpdateIrrigationZoneCommand toCommand(UpdateIrrigationZoneCommandDTO dto);

    CreateIrrigationZoneCommand toCommand(CreateIrrigationZoneCommandDTO dto);

    DeleteIrrigationZoneCommand toCommand(DeleteIrrigationZoneCommandDTO dto);
}

package pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;

public interface IrrigationZoneDTOMapper {

    IrrigationZoneDTO toDto(IrrigationZone model);
}

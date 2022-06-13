package pt.sensae.services.identity.management.backend.application.mapper.device;

import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.model.device.ExpelDeviceFromDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.device.PlaceDeviceInDomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;
import pt.sensae.services.identity.management.backend.domainservices.model.device.PlaceDeviceInDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.device.RemoveDeviceFromDomainCommand;

public interface DeviceMapper {

    PlaceDeviceInDomainCommand dtoToCommand(PlaceDeviceInDomainDTO dto);
    
    RemoveDeviceFromDomainCommand dtoToCommand(ExpelDeviceFromDomainDTO dto);
    
    DeviceIdDTO resultToDto(DeviceResult result);
}

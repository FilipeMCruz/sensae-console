package sharespot.services.identitymanagementbackend.application.mapper.device;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.model.device.ExpelDeviceFromDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.device.PlaceDeviceInDomainDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.PlaceDeviceInDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.device.RemoveDeviceFromDomainCommand;

public interface DeviceMapper {

    PlaceDeviceInDomainCommand dtoToCommand(PlaceDeviceInDomainDTO dto);
    
    RemoveDeviceFromDomainCommand dtoToCommand(ExpelDeviceFromDomainDTO dto);
    
    DeviceIdDTO resultToDto(DeviceResult result);
}

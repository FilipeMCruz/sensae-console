package sharespot.services.identitymanagementbackend.application.mapper.device;

import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.device.PlaceDeviceInDomainCommand;

public interface DeviceMapper {

    PlaceDeviceInDomainCommand dtoToCommand(NewDomainForDeviceDTO dto);
}

package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.device.PlaceDeviceInDomainCommand;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.NewDomainForDeviceDTOImpl;

@Service
public class DeviceMapperImpl implements DeviceMapper {
    @Override
    public PlaceDeviceInDomainCommand dtoToCommand(NewDomainForDeviceDTO dto) {
        var info = (NewDomainForDeviceDTOImpl) dto;
        var command = new PlaceDeviceInDomainCommand();
        command.newDomain = info.domainOid;
        command.device = info.tenantOid;
        command.writePermission = info.writePermission;
        return command;
    }
}

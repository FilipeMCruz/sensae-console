package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DevicePermissionsResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.PlaceDeviceInDomainCommand;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.DeviceDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.DeviceDomainPermissionsDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.DevicePermissionsDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.NewDomainForDeviceDTOImpl;

import java.util.UUID;

@Service
public class DeviceMapperImpl implements DeviceMapper {
    @Override
    public PlaceDeviceInDomainCommand dtoToCommand(NewDomainForDeviceDTO dto) {
        var info = (NewDomainForDeviceDTOImpl) dto;
        var command = new PlaceDeviceInDomainCommand();
        command.newDomain = UUID.fromString(info.domainOid);
        command.device = UUID.fromString(info.deviceOid);
        command.writePermission = info.writePermission;
        return command;
    }

    @Override
    public DeviceDTO resultToDto(DeviceResult result) {
        var dto = new DeviceDTOImpl();
        dto.oid = result.oid;
        dto.domains = result.domains.stream().map(d -> {
            var deviceDomainPermissionsDTO = new DeviceDomainPermissionsDTOImpl();
            deviceDomainPermissionsDTO.oid = d.oid;
            deviceDomainPermissionsDTO.permissions = d.permissions.equals(DevicePermissionsResult.READ) ?
                    DevicePermissionsDTOImpl.READ : DevicePermissionsDTOImpl.READ_WRITE;
            return deviceDomainPermissionsDTO;
        }).toList();
        return dto;
    }
}

package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.mapper.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.model.device.ExpelDeviceFromDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.device.PlaceDeviceInDomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;
import pt.sensae.services.identity.management.backend.domainservices.model.device.PlaceDeviceInDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.device.RemoveDeviceFromDomainCommand;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device.DeviceDomainPermissionsDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device.DeviceIdDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device.ExpelDeviceFromDomainDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device.PlaceDeviceInDomainDTOImpl;
import pt.sensae.services.identity.management.backend.application.mapper.device.DeviceMapper;

import java.util.UUID;

@Service
public class DeviceMapperImpl implements DeviceMapper {
    @Override
    public PlaceDeviceInDomainCommand dtoToCommand(PlaceDeviceInDomainDTO dto) {
        var info = (PlaceDeviceInDomainDTOImpl) dto;
        var command = new PlaceDeviceInDomainCommand();
        command.newDomain = UUID.fromString(info.domainOid);
        command.device = UUID.fromString(info.deviceOid);
        return command;
    }

    @Override
    public RemoveDeviceFromDomainCommand dtoToCommand(ExpelDeviceFromDomainDTO dto) {
        var info = (ExpelDeviceFromDomainDTOImpl) dto;
        var command = new RemoveDeviceFromDomainCommand();
        command.domain = UUID.fromString(info.domainOid);
        command.device = UUID.fromString(info.deviceOid);
        return command;
    }

    @Override
    public DeviceIdDTO resultToDto(DeviceResult result) {
        var dto = new DeviceIdDTOImpl();
        dto.oid = result.oid;
        dto.name = result.name;
        dto.domains = result.domains.stream().map(d -> {
            var deviceDomainPermissionsDTO = new DeviceDomainPermissionsDTOImpl();
            deviceDomainPermissionsDTO.oid = d.oid;
            return deviceDomainPermissionsDTO;
        }).toList();
        return dto;
    }
}

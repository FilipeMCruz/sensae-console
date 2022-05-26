package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.device.ViewTenantDevices;

import java.util.stream.Stream;

@Service
public class ViewTenantDevicesService {

    private final ViewTenantDevices service;

    private final TenantMapper tenantMapper;

    private final DeviceMapper deviceMapper;


    public ViewTenantDevicesService(ViewTenantDevices service, TenantMapper tenantMapper, DeviceMapper deviceMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.deviceMapper = deviceMapper;
    }

    public Stream<DeviceIdDTO> fetch(AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        return service.fetch(identityCommand)
                .map(deviceMapper::resultToDto);
    }
}

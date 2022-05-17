package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.DeviceInformationNotifierService;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.model.device.ExpelDeviceFromDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.device.PlaceDeviceInDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domainservices.service.device.MoveDevice;

@Service
public class PlaceDeviceInDomainService {

    private final MoveDevice service;

    private final TenantMapper tenantMapper;

    private final DeviceMapper deviceMapper;

    private final DeviceInformationNotifierService emitter;

    public PlaceDeviceInDomainService(MoveDevice service,
                                      TenantMapper tenantMapper,
                                      DeviceMapper deviceMapper,
                                      DeviceInformationNotifierService emitter) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.deviceMapper = deviceMapper;
        this.emitter = emitter;
    }

    public DeviceIdDTO place(PlaceDeviceInDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = deviceMapper.dtoToCommand(dto);
        var result = deviceMapper.resultToDto(service.execute(command, identityCommand));
        emitter.notify(result);
        return result;
    }

    public DeviceIdDTO expel(ExpelDeviceFromDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = deviceMapper.dtoToCommand(dto);
        var result = deviceMapper.resultToDto(service.execute(command, identityCommand));
        emitter.notify(result);
        return result;
    }
}

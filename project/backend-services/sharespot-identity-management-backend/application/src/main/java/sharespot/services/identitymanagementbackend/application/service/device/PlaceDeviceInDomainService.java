package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
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

    private final DeviceUpdateHandlerService emitter;

    public PlaceDeviceInDomainService(MoveDevice service,
                                      TenantMapper tenantMapper,
                                      DeviceMapper deviceMapper,
                                      DeviceUpdateHandlerService emitter) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.deviceMapper = deviceMapper;
        this.emitter = emitter;
    }

    public DeviceDTO place(PlaceDeviceInDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = deviceMapper.dtoToCommand(dto);
        var result = deviceMapper.resultToDto(service.execute(command, identityCommand));
        emitter.publishUpdate(DeviceId.of(command.device));
        return result;
    }

    public DeviceDTO expel(ExpelDeviceFromDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = deviceMapper.dtoToCommand(dto);
        var result = deviceMapper.resultToDto(service.execute(command, identityCommand));
        emitter.publishUpdate(DeviceId.of(command.device));
        return result;
    }
}

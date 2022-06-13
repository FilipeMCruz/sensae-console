package pt.sensae.services.identity.management.backend.application.service.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.identity.device.DeviceIdentityNotifierService;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.device.ExpelDeviceFromDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.device.DeviceMapper;
import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.model.device.PlaceDeviceInDomainDTO;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domainservices.service.device.MoveDevice;

import java.util.UUID;

@Service
public class PlaceDeviceInDomainService {

    private final MoveDevice service;

    private final TenantMapper tenantMapper;

    private final DeviceMapper deviceMapper;

    private final DeviceIdentityNotifierService emitter;

    public PlaceDeviceInDomainService(MoveDevice service,
                                      TenantMapper tenantMapper,
                                      DeviceMapper deviceMapper,
                                      DeviceIdentityNotifierService emitter) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.deviceMapper = deviceMapper;
        this.emitter = emitter;
    }

    public DeviceIdDTO place(PlaceDeviceInDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = deviceMapper.dtoToCommand(dto);
        var result = service.execute(command, identityCommand);
        emitter.notify(new DeviceId(UUID.fromString(result.oid)));
        return deviceMapper.resultToDto(result);
    }

    public DeviceIdDTO expel(ExpelDeviceFromDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = deviceMapper.dtoToCommand(dto);
        var result = service.execute(command, identityCommand);
        emitter.notify(new DeviceId(UUID.fromString(result.oid)));
        return deviceMapper.resultToDto(result);
    }
}

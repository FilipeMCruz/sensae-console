package pt.sensae.services.identity.management.backend.domainservices.service.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.device.DeviceInformation;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DeviceResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;
import pt.sensae.services.identity.management.backend.domainservices.model.device.PlaceDeviceInDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.device.RemoveDeviceFromDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceRepository;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;

import java.util.List;

@Service
public class MoveDevice {

    private final DomainRepository domainRepo;

    private final DeviceRepository deviceRepo;
    
    private final DeviceInformationCache deviceInformationCache;

    public MoveDevice(DomainRepository domainRepo, DeviceRepository deviceRepo, DeviceInformationCache deviceInformationCache) {
        this.domainRepo = domainRepo;
        this.deviceRepo = deviceRepo;
        this.deviceInformationCache = deviceInformationCache;
    }

    public DeviceResult execute(PlaceDeviceInDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var domainId = DomainId.of(command.newDomain);
        var deviceId = DeviceId.of(command.device);

        var domain = domainRepo.findDomainById(domainId)
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.EDIT_DEVICE));

        var device = deviceRepo.findDeviceById(deviceId)
                .orElseThrow(NotValidException.withMessage("Invalid Device"));

        device.domains().add(domainId);

        var relocateDevice = deviceRepo.relocateDevice(device);

        var information = deviceInformationCache.findById(relocateDevice.oid());

        return DeviceResultMapper.toResult(relocateDevice, information);
    }

    public DeviceResult execute(RemoveDeviceFromDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var domainId = DomainId.of(command.domain);
        var deviceId = DeviceId.of(command.device);

        var domain = domainRepo.findDomainById(domainId)
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.EDIT_DEVICE));

        var device = deviceRepo.findDeviceById(deviceId)
                .orElseThrow(NotValidException.withMessage("Invalid Device"));

        device.domains().removeIf(d -> d.equals(domainId));

        if (device.domains().isEmpty()) {
            var rootDomain = domainRepo.getRootDomain();
            device.domains().add(rootDomain.getOid());
        }

        var relocateDevice = deviceRepo.relocateDevice(device);

        var information = deviceInformationCache.findById(relocateDevice.oid());
        
        return DeviceResultMapper.toResult(relocateDevice, information);
    }
}

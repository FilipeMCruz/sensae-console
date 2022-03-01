package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceDomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domain.identity.device.DevicePermissions;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.device.PlaceDeviceInDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.device.RemoveDeviceFromDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.List;

@Service
public class MoveDevice {

    private final DomainRepository domainRepo;

    private final DeviceRepository deviceRepo;

    public MoveDevice(DomainRepository domainRepo, DeviceRepository deviceRepo) {
        this.domainRepo = domainRepo;
        this.deviceRepo = deviceRepo;
    }

    public DeviceResult execute(PlaceDeviceInDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var domainId = DomainId.of(command.newDomain);
        var deviceId = DeviceId.of(command.device);

        var domain = domainRepo.findDomainById(domainId)
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.WRITE_DEVICE));

        var device = deviceRepo.findDeviceById(deviceId)
                .orElseThrow(NotValidException.withMessage("Invalid Device"));

        device.getDomains().add(new DeviceDomainPermissions(domainId, command.writePermission ?
                DevicePermissions.READ_WRITE : DevicePermissions.READ));

        var relocateDevice = deviceRepo.relocateDevice(device);
        return DeviceResultMapper.toResult(relocateDevice);
    }

    public DeviceResult execute(RemoveDeviceFromDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var domainId = DomainId.of(command.domain);
        var deviceId = DeviceId.of(command.device);

        var domain = domainRepo.findDomainById(domainId)
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.WRITE_DEVICE));

        var device = deviceRepo.findDeviceById(deviceId)
                .orElseThrow(NotValidException.withMessage("Invalid Device"));

        device.getDomains().removeIf(d -> d.domain().equals(domainId));

        if (device.getDomains().isEmpty()) {
            var rootDomain = domainRepo.getRootDomain();
            device.getDomains().add(new DeviceDomainPermissions(rootDomain.getOid(), DevicePermissions.READ_WRITE));
        }

        var relocateDevice = deviceRepo.relocateDevice(device);
        return DeviceResultMapper.toResult(relocateDevice);
    }
}

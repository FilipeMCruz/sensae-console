package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceDomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domain.identity.device.DevicePermissions;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.model.device.PlaceDeviceInDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

@Service
public class PlaceDeviceInDomain {

    private final TenantRepository tenantRepo;

    private final DomainRepository domainRepo;

    private final DeviceRepository deviceRepo;

    public PlaceDeviceInDomain(TenantRepository tenantRepo, DomainRepository domainRepo, DeviceRepository deviceRepo) {
        this.tenantRepo = tenantRepo;
        this.domainRepo = domainRepo;
        this.deviceRepo = deviceRepo;
    }

    public void execute(PlaceDeviceInDomainCommand command, IdentityCommand identity) {
        var tenant = tenantRepo.findTenantById(TenantId.of(identity.oid))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        var domainId = DomainId.of(command.newDomain);
        var domain = domainRepo.findDomainById(domainId)
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain);

        var device = deviceRepo.findDeviceById(DeviceId.of(command.device))
                .orElseThrow(NotValidException.withMessage("Invalid Device"));

        if (device.getDomains().stream().anyMatch(d -> d.domain().equals(domainId))) {
            throw new NotValidException("Device already in Domain");
        }

        device.getDomains().add(new DeviceDomainPermissions(domainId, command.writePermission ? DevicePermissions.READ_WRITE : DevicePermissions.READ));
        deviceRepo.relocateDevice(device);
    }
}

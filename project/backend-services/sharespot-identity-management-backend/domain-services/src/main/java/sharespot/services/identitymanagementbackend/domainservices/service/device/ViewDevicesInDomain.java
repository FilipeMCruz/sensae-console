package sharespot.services.identitymanagementbackend.domainservices.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DeviceResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.mapper.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ViewDomainQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ViewDevicesInDomain {

    private final DomainRepository domainRepo;

    private final DeviceRepository deviceRepo;

    public ViewDevicesInDomain(DomainRepository domainRepo, DeviceRepository deviceRepo) {
        this.domainRepo = domainRepo;
        this.deviceRepo = deviceRepo;
    }

    public Stream<DeviceResult> fetch(ViewDomainQuery query, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var topId = DomainId.of(query.topDomainId);
        var top = domainRepo.findDomainById(topId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, top, List.of(PermissionType.READ_DEVICE));

        return deviceRepo.getDevicesInDomains(Stream.of(top.getOid()))
                .map(DeviceResultMapper::toResult);
    }
}

package pt.sensae.services.identity.management.backend.domainservices.service.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DeviceResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceRepository;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;

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

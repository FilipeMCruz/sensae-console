package sharespot.services.identitymanagementbackend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DeviceResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainInfoResult;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DomainResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ViewDomainQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.mapper.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.List;

@Service
public class ViewDomainInfo {

    private final DomainRepository domainRepo;

    private final TenantRepository tenantRepo;

    private final DeviceRepository deviceRepo;

    public ViewDomainInfo(DomainRepository domainRepo, TenantRepository tenantRepo, DeviceRepository deviceRepo) {
        this.domainRepo = domainRepo;
        this.tenantRepo = tenantRepo;
        this.deviceRepo = deviceRepo;
    }

    public DomainInfoResult fetch(ViewDomainQuery query, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var topId = DomainId.of(query.topDomainId);
        var top = domainRepo.findDomainById(topId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, top, List.of(PermissionType.READ_DOMAINS));

        var domainResult = DomainResultMapper.toResult(top);
        var tenantResult = tenantRepo
                .getTenantsInDomain(top.getOid())
                .map(TenantResultMapper::toResult)
                .toList();

        var deviceResults = deviceRepo
                .getDevicesInDomain(top.getOid())
                .map(DeviceResultMapper::toResult)
                .toList();

        var domainInfoResult = new DomainInfoResult();
        domainInfoResult.domain = domainResult;
        domainInfoResult.devices = deviceResults;
        domainInfoResult.tenants = tenantResult;
        return domainInfoResult;
    }
}

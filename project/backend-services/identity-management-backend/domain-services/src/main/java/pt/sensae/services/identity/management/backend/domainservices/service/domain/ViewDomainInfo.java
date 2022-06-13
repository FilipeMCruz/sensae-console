package pt.sensae.services.identity.management.backend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DeviceResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DomainResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceRepository;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantRepository;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainInfoResult;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;
import pt.sensae.services.identity.management.backend.domainservices.service.device.DeviceInformationCache;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ViewDomainInfo {

    private final DomainRepository domainRepo;

    private final TenantRepository tenantRepo;

    private final DeviceRepository deviceRepo;

    private final DeviceInformationCache deviceInformationCache;

    public ViewDomainInfo(DomainRepository domainRepo,
                          TenantRepository tenantRepo,
                          DeviceRepository deviceRepo,
                          DeviceInformationCache deviceInformationCache) {
        this.domainRepo = domainRepo;
        this.tenantRepo = tenantRepo;
        this.deviceRepo = deviceRepo;
        this.deviceInformationCache = deviceInformationCache;
    }

    public DomainInfoResult fetch(ViewDomainQuery query, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var topId = DomainId.of(query.topDomainId);
        var top = domainRepo.findDomainById(topId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, top,
                List.of(PermissionType.READ_DOMAIN,
                        PermissionType.READ_DEVICE,
                        PermissionType.READ_TENANT));

        var domainResult = DomainResultMapper.toResult(top);
        var tenantResult = tenantRepo
                .getTenantsInDomain(top.getOid())
                .map(TenantResultMapper::toResult)
                .toList();

        var deviceResults = deviceRepo
                .getDevicesInDomains(Stream.of(top.getOid()))
                .map(d -> DeviceResultMapper.toResult(d, this.deviceInformationCache.findById(d.oid())))
                .toList();

        var domainInfoResult = new DomainInfoResult();
        domainInfoResult.domain = domainResult;
        domainInfoResult.devices = deviceResults;
        domainInfoResult.tenants = tenantResult;
        return domainInfoResult;
    }
}

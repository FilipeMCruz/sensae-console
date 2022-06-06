package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.TenantResult;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantRepository;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ViewTenantsInDomain {

    private final DomainRepository domainRepo;

    private final TenantRepository tenantRepo;

    public ViewTenantsInDomain(DomainRepository domainRepo, TenantRepository deviceRepo) {
        this.domainRepo = domainRepo;
        this.tenantRepo = deviceRepo;
    }

    public Stream<TenantResult> fetch(ViewDomainQuery query, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var topId = DomainId.of(query.topDomainId);
        var top = domainRepo.findDomainById(topId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, top, List.of(PermissionType.READ_TENANT));

        return tenantRepo.getTenantsInDomain(top.getOid())
                .map(TenantResultMapper::toResult);
    }
}

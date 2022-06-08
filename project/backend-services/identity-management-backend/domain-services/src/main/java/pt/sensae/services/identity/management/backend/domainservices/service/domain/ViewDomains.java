package pt.sensae.services.identity.management.backend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DomainResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ViewDomains {

    private final DomainRepository domainRepo;

    public ViewDomains(DomainRepository domainRepo) {
        this.domainRepo = domainRepo;
    }

    public Stream<DomainResult> fetch(ViewDomainQuery query, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);
        var topId = DomainId.of(query.topDomainId);
        var top = domainRepo.findDomainById(topId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, top, List.of(PermissionType.READ_DOMAIN));

        return domainRepo.getChildDomains(topId)
                .map(DomainResultMapper::toResult);
    }
}

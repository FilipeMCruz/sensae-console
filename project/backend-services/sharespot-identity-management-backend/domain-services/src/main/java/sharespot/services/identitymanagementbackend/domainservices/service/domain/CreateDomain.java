package sharespot.services.identitymanagementbackend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.*;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.DomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DomainResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.mapper.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateDomain {

    private final DomainRepository domainRepo;

    public CreateDomain(DomainRepository domainRepo) {
        this.domainRepo = domainRepo;
    }

    public DomainResult execute(CreateDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);

        var parentDomainId = DomainId.of(command.parentDomainId);
        var parentDomain = domainRepo.findDomainById(parentDomainId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, parentDomain, List.of(PermissionType.EDIT_DOMAIN));

        var domainName = DomainName.of(command.domainName);
        if (domainName.isUnallocated()) {
            throw new NotValidException("Invalid Domain Name: unallocated is reserved");
        }

        var domainId = DomainId.of(command.domainId);
        var domainPath = new ArrayList<>(parentDomain.getPath().path());
        domainPath.add(domainId);
        var domain = new Domain(domainId, domainName, DomainPath.of(domainPath), DomainPermissions.empty());

        if (domainRepo.getChildDomains(parentDomainId).anyMatch(domain::same)) {
            throw new NotValidException("Invalid Domain Name: Name Already used");
        }

        var newDomain = domainRepo.addDomain(domain);
        if (parentDomain.isRoot()) {
            domainRepo.addDomain(Domain.unallocated(newDomain));
        }

        return DomainResultMapper.toResult(newDomain);
    }
}

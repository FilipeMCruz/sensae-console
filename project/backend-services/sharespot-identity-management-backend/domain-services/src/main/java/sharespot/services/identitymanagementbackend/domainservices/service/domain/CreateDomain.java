package sharespot.services.identitymanagementbackend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.*;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.ArrayList;

@Service
public class CreateDomain {

    private final TenantRepository identityRepo;

    private final DomainRepository domainRepo;

    public CreateDomain(TenantRepository identityRepo, DomainRepository domainRepo) {
        this.identityRepo = identityRepo;
        this.domainRepo = domainRepo;
    }

    public DomainResult execute(CreateDomainCommand command, IdentityCommand identity) {
        var tenant = identityRepo.findTenantById(TenantId.of(identity.oid))
                .orElseThrow(NotValidException.withMessage("Invalid Tenant"));

        var parentDomainId = DomainId.of(command.parentDomainId);
        var parentDomain = domainRepo.findDomainById(parentDomainId)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, parentDomain);

        var domainName = DomainName.of(command.domainName);
        if (domainName.isUnallocated()) {
            throw new NotValidException("Invalid Domain Name: unallocated is reserved");
        }

        var domainId = DomainId.of(command.domainId);
        var domainPath = new ArrayList<>(parentDomain.getPath().path());
        domainPath.add(domainId);
        var domain = new Domain(domainId, domainName, DomainPath.of(domainPath));

        if (domainRepo.getChildDomains(parentDomainId).stream().anyMatch(domain::same)) {
            throw new NotValidException("Invalid Domain Name: Name Already used");
        }

        var newDomain = domainRepo.addDomain(domain);
        if (parentDomain.isRoot()) {
            domainRepo.addDomain(Domain.unallocated(domain));
        }
        
        var domainResult = new DomainResult();
        domainResult.domainId = newDomain.getOid().value();
        domainResult.domainName = newDomain.getName().value();
        domainResult.path = newDomain.getPath()
                .path()
                .stream()
                .map(d -> d.value().toString())
                .toList();
        return domainResult;
    }
}

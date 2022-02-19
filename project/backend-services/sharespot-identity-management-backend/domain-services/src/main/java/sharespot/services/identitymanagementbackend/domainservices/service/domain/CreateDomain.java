package sharespot.services.identitymanagementbackend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.*;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.CreateDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class CreateDomain {

    private final TenantRepository identityRepo;

    private final DomainRepository domainRepo;

    public CreateDomain(TenantRepository identityRepo, DomainRepository domainRepo) {
        this.identityRepo = identityRepo;
        this.domainRepo = domainRepo;
    }

    public void execute(CreateDomainCommand command, IdentityCommand identity) {
        var tenantOpt = identityRepo.findTenantById(new TenantId(identity.oid));
        if (tenantOpt.isEmpty()) {
            throw new NotValidException("Invalid Tenant");
        }
        var tenant = tenantOpt.get();
        var parentId = new DomainId(command.parentDomainId);
        var parentDomainOpt = domainRepo.findDomainById(parentId);
        if (parentDomainOpt.isEmpty()) {
            throw new NotValidException("Invalid Parent Domain");
        }
        var parentDomain = parentDomainOpt.get();
        var parentDomainIds = parentDomain.getDomainPath().path();
        if (tenant.getDomains().stream().noneMatch(parentDomainIds::contains)) {
            throw new NotValidException("No permissions");
        }

        var children = domainRepo.getChildDomains(parentId);

        var domainName = new DomainName(command.domainName);
        var domainId = new DomainId(command.domainId);

        if (children.stream().anyMatch(d -> d.getName().equals(domainName) || d.getId().equals(domainId))) {
            throw new NotValidException("Duplicate Child Domain");
        }
        var domainPath = new ArrayList<>(parentDomainIds);
        domainPath.add(domainId);

        var domain = new Domain(domainName, domainId, new DomainPath(domainPath));
        domainRepo.addDomain(domain);

        if (parentDomain.isRoot()) {
            var unallocatedDomainName = new DomainName(command.domainName + "unallocated");
            var unallocatedDomainId = new DomainId(UUID.randomUUID());
            var unallocatedDomainPath = new ArrayList<>(parentDomainIds);
            unallocatedDomainPath.add(unallocatedDomainId);
            var unallocatedDomain = new Domain(unallocatedDomainName, unallocatedDomainId, new DomainPath(unallocatedDomainPath));
            domainRepo.addDomain(unallocatedDomain);
        }
    }
}

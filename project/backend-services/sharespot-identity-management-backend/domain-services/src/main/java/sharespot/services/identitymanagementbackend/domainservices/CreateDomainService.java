package sharespot.services.identitymanagementbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.model.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.model.identity.domain.*;
import sharespot.services.identitymanagementbackend.domain.model.identity.tenant.TenantId;
import sharespot.services.identitymanagementbackend.domain.model.identity.tenant.TenantRepository;

import java.util.ArrayList;

@Service
public class CreateDomainService {

    private final TenantRepository identityRepo;
    private final DomainRepository domainRepo;

    public CreateDomainService(TenantRepository identityRepo, DomainRepository domainRepo) {
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
        var parentDomains = domainRepo.findDomainById(parentId);
        if (parentDomains.isEmpty()) {
            throw new NotValidException("Invalid Parent Domain");
        }
        var parentDomainIds = parentDomains.get().getDomainPath().path();
        if (tenant.getDomains().stream().noneMatch(parentDomainIds::contains)) {
            throw new NotValidException("No permissions");
        }

        var childs = domainRepo.getChildDomains(parentId);

        var domainName = new DomainName(command.domainName);
        var domainId = new DomainId(command.domainId);

        if (childs.stream().anyMatch(d -> d.getName().equals(domainName) || d.getId().equals(domainId))) {
            throw new NotValidException("Duplicate Child Domain");
        }
        var domainPath = new ArrayList<>(parentDomainIds);
        domainPath.add(domainId);

        var domain = new Domain(domainName, domainId, new DomainPath(domainPath));
        domainRepo.addDomain(domain);
    }
}

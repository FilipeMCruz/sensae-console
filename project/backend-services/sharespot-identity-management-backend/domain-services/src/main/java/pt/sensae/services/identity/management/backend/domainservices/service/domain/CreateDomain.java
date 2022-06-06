package pt.sensae.services.identity.management.backend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.domain.*;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DomainResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.DomainPermissions;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.CreateDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;

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
                .filter(Domain::isValidParentDomain)
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, parentDomain, List.of(PermissionType.EDIT_DOMAIN));

        var domainName = DomainName.of(command.domainName);
        if (domainName.isValidName()) {
            throw new NotValidException("Invalid Domain Name: unallocated/public/root is reserved");
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

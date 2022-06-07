package pt.sensae.services.identity.management.backend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.mapper.DomainResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.PermissionsMapper;
import pt.sensae.services.identity.management.backend.domainservices.mapper.TenantResultMapper;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ChangeDomainCommand;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.IdentityCommand;
import pt.sensae.services.identity.management.backend.domainservices.service.PermissionsValidator;
import pt.sensae.services.identity.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.identity.management.backend.domain.identity.domain.Domain;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainName;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainRepository;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.DomainPermissions;
import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChangeDomain {

    private final DomainRepository repository;

    public ChangeDomain(DomainRepository repository) {
        this.repository = repository;
    }

    public DomainResult execute(ChangeDomainCommand command, IdentityCommand identity) {
        var tenant = TenantResultMapper.toDomain(identity);

        var domainId = DomainId.of(command.domainId);
        var domain = repository.findDomainById(domainId)
                .orElseThrow(NotValidException.withMessage("Invalid Domain"));

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.EDIT_DOMAIN));

        if (domain.isRoot() || domain.isUnallocated()) {
            throw new NotValidException("Invalid Domain: Can't change root or unallocated domain's permissions");
        }

        var parentDomain = repository.findDomainById(domain.getPath().getParent())
                .orElseThrow(NotValidException.withMessage("Invalid Parent Domain"));

        PermissionsValidator.verifyPermissions(tenant, parentDomain, List.of(PermissionType.READ_DOMAIN));

        var domainName = DomainName.of(command.domainName);
        if (domainName.isUnallocated()) {
            throw new NotValidException("Invalid Domain Name: unallocated is reserved");
        }

        var availablePermissions = parentDomain.getPermissions().values();

        var permissions = PermissionsMapper.toDomain(command.permissions.stream())
                .filter(availablePermissions::contains)
                .collect(Collectors.toSet());

        PermissionType.reviewPermissions(permissions);

        var updated = new Domain(domainId, domainName, domain.getPath(), DomainPermissions.of(permissions));

        var changedDomain = repository.changeDomain(updated);

        updateChildPermissions(repository.getChildDomains(domainId), permissions);

        return DomainResultMapper.toResult(changedDomain);
    }

    private void updateChildPermissions(Stream<Domain> children, Set<PermissionType> availablePermissions) {
        children.forEach(d -> {
            var newPermissions = d.getPermissions()
                    .values()
                    .stream()
                    .filter(availablePermissions::contains)
                    .collect(Collectors.toSet());
            if (newPermissions.size() != d.getPermissions().values().size()) {
                repository.changeDomain(new Domain(d.getOid(), d.getName(), d.getPath(), DomainPermissions.of(newPermissions)));
            }
        });
    }
}

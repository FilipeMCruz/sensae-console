package sharespot.services.identitymanagementbackend.domainservices.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.exceptions.NotValidException;
import sharespot.services.identitymanagementbackend.domain.identity.domain.Domain;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainName;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainRepository;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.DomainPermissions;
import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;
import sharespot.services.identitymanagementbackend.domainservices.mapper.DomainResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.mapper.PermissionsMapper;
import sharespot.services.identitymanagementbackend.domainservices.mapper.TenantResultMapper;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ChangeDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.service.PermissionsValidator;

import java.util.List;
import java.util.stream.Collectors;

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

        PermissionsValidator.verifyPermissions(tenant, domain, List.of(PermissionType.WRITE_DOMAIN));

        if (domain.isRoot()) {
            throw new NotValidException("Invalid Domain: Can't change root permissions");
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

        if (permissions.contains(PermissionType.WRITE_DEVICE_RECORD)) {
            permissions.add(PermissionType.READ_DEVICE_RECORD);
        }

        if (permissions.contains(PermissionType.WRITE_DATA_TRANSFORMATION)) {
            permissions.add(PermissionType.READ_DATA_TRANSFORMATION);
        }

        if (permissions.contains(PermissionType.WRITE_DEVICE)) {
            permissions.add(PermissionType.READ_DEVICE);
        }

        if (permissions.contains(PermissionType.WRITE_DOMAIN)) {
            permissions.add(PermissionType.READ_DOMAIN);
        }

        if (permissions.contains(PermissionType.WRITE_TENANT)) {
            permissions.add(PermissionType.READ_TENANT);
        }

        var updated = new Domain(domainId, domainName, domain.getPath(), DomainPermissions.of(permissions));

        var changedDomain = repository.changeDomain(updated);
        return DomainResultMapper.toResult(changedDomain);
    }
}

package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain;

import sharespot.services.identitymanagementbackend.domain.identity.permissions.PermissionType;

import javax.persistence.*;

@Entity(name = "domain_permission")
public class DomainPermissionPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Embedded
    public DomainPermissionTypePostgres permission;

    @ManyToOne
    public DomainPostgres domain;

    public static DomainPermissionPostgres create(PermissionType type) {
        var domainPermissionTypePostgres = new DomainPermissionPostgres();
        domainPermissionTypePostgres.permission = DomainPermissionTypePostgres.create(type);
        return domainPermissionTypePostgres;
    }
    
    public static PermissionType from(DomainPermissionPostgres postgres) {
        return DomainPermissionTypePostgres.from(postgres.permission);
    }
}

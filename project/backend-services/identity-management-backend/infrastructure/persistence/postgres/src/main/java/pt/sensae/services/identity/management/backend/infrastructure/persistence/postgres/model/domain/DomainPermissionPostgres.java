package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.domain;

import pt.sensae.services.identity.management.backend.domain.identity.permissions.PermissionType;

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

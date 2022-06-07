package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "domain")
public class DomainPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String oid;

    public String name;

    @Column(columnDefinition = "text[]")
    @Type(type = "pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.repository.util.GenericArrayUserType")
    public String[] path;

    @OneToMany(mappedBy = "domain", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DomainPermissionPostgres> permissions;
}

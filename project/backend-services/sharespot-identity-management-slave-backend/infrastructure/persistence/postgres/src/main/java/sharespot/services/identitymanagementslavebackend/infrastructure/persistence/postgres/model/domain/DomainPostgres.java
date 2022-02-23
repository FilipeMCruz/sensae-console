package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "domain")
public class DomainPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String oid;

    public String name;

    @Column(columnDefinition = "text[]")
    @Type(type = "sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.repository.util.GenericArrayUserType")
    public String[] path;
}

package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.tenant;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "tenant")
public class TenantPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String oid;

    @Column(unique = true)
    public String email;

    public String name;

    @Column(columnDefinition = "text[]")
    @Type(type = "sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.util.GenericArrayUserType")
    public String[] domains;
}

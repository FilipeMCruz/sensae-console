package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.tenant;

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

    @Column(name = "phone_number")
    public String phoneNumber;

    public String name;

    @Column(columnDefinition = "text[]")
    @Type(type = "pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.repository.util.GenericArrayUserType")
    public String[] domains;
}

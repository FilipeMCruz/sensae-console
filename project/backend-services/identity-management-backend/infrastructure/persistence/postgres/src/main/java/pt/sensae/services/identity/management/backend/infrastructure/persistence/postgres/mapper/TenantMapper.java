package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.*;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.tenant.TenantPostgres;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class TenantMapper {

    public static TenantPostgres domainToPostgres(Tenant tenant) {
        var tenantPostgres = new TenantPostgres();
        tenantPostgres.email = tenant.email().value();
        tenantPostgres.phoneNumber = tenant.phoneNumber().value();
        tenantPostgres.name = tenant.name().value();
        tenantPostgres.oid = tenant.oid().value().toString();
        tenantPostgres.domains = tenant.domains()
                .stream()
                .map(d -> d.value().toString())
                .toList()
                .toArray(new String[0]);
        return tenantPostgres;
    }

    public static Tenant postgresToDomain(TenantPostgres postgres) {
        var name = TenantName.of(postgres.name);
        var oid = TenantId.of(UUID.fromString(postgres.oid));
        var email = TenantEmail.of(postgres.email);
        var phoneNumber = postgres.phoneNumber == null || postgres.phoneNumber.isBlank() ?
                TenantPhoneNumber.empty() : TenantPhoneNumber.of(postgres.phoneNumber);
        var domains = Arrays.stream(postgres.domains).map(d -> DomainId.of(UUID.fromString(d)))
                .collect(Collectors.toList());
        return new Tenant(oid, name, email, phoneNumber, domains);
    }
}

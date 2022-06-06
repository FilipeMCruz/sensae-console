package pt.sensae.services.identity.management.backend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.TenantRepository;

import java.util.stream.Stream;

@Service
public class CollectAllTenants {
    private final TenantRepository repository;

    public CollectAllTenants(TenantRepository repository) {
        this.repository = repository;
    }
    
    public Stream<Tenant> collect() {
        return repository.findAll();
    }
}

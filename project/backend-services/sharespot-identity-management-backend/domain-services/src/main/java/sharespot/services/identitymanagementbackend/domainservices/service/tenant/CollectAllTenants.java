package sharespot.services.identitymanagementbackend.domainservices.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.TenantRepository;

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

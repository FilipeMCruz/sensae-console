package pt.sensae.services.notification.dispatcher.backend.application.tenant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.DomainId;
import pt.sensae.services.notification.dispatcher.backend.domain.Domains;
import pt.sensae.services.notification.dispatcher.backend.domain.tenant.Tenant;
import pt.sensae.services.notification.dispatcher.backend.domain.tenant.TenantRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TenantRepositoryImpl implements TenantRepository {

    private final Cache<DomainId, Set<Tenant>> cache;

    private boolean isSynced;

    public TenantRepositoryImpl() {
        this.cache = Caffeine.newBuilder().build();
        this.isSynced = false;
    }

    @Override
    public Stream<Tenant> findTenantsInDomains(Domains domains) {
        return this.cache.getAllPresent(domains.value())
                .values()
                .stream()
                .flatMap(Collection::stream);
    }

    @Override
    public void index(Tenant tenant) {
        this.cache.asMap().values().forEach(tenants -> tenants.remove(tenant));
        tenant.domains().value().forEach(domain -> {
            var ifPresent = this.cache.getIfPresent(domain);
            if (ifPresent == null) {
                this.cache.put(domain, Set.of(tenant));
            } else {
                ifPresent.add(tenant);
            }
        });
    }

    @Override
    public void refresh(Set<Tenant> tenants) {
        if (this.isSynced) {
            return;
        }
        var map = new HashMap<DomainId, Set<Tenant>>();
        tenants.forEach(tenant -> tenant.domains()
                .value()
                .forEach(domain -> map.computeIfAbsent(domain, k -> new HashSet<>()).add(tenant)));

        this.cache.invalidateAll();
        this.cache.putAll(map);
        this.isSynced = true;
    }
}

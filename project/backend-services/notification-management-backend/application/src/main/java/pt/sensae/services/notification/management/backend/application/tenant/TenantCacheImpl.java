package pt.sensae.services.notification.management.backend.application.tenant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantCache;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TenantCacheImpl implements TenantCache {

    private final Cache<DomainId, Set<Tenant>> cache;

    public TenantCacheImpl() {
        this.cache = Caffeine.newBuilder().build();
    }

    @Override
    public Stream<Tenant> findTenantsInDomains(Domains domains) {
        return this.cache.getAllPresent(domains.value())
                .values()
                .stream()
                .flatMap(Collection::stream);
    }

    @Override
    public Tenant index(Tenant tenant) {
        this.cache.asMap().values().forEach(tenants -> tenants.remove(tenant));
        tenant.domains().value().forEach(domain -> {
            var ifPresent = this.cache.getIfPresent(domain);
            if (ifPresent == null) {
                this.cache.put(domain, Set.of(tenant));
            } else {
                ifPresent.add(tenant);
            }
        });
        return tenant;
    }

    @Override
    public void refresh(Set<Tenant> tenants) {
        var map = new HashMap<DomainId, Set<Tenant>>();
        tenants.forEach(tenant -> tenant.domains()
                .value()
                .forEach(domain -> map.computeIfAbsent(domain, k -> new HashSet<>()).add(tenant)));

        this.cache.invalidateAll();
        this.cache.putAll(map);
    }
}

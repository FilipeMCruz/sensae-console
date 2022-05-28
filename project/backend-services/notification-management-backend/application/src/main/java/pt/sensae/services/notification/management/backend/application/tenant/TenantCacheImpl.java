package pt.sensae.services.notification.management.backend.application.tenant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.domain.DomainId;
import pt.sensae.services.notification.management.backend.domain.Domains;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.tenant.Tenant;
import pt.sensae.services.notification.management.backend.domain.tenant.TenantCache;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TenantCacheImpl implements TenantCache {

    private final Cache<AddresseeId, Tenant> cache;

    private final Cache<DomainId, Set<AddresseeId>> helperCache;

    public TenantCacheImpl() {
        this.helperCache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    @Override
    public Optional<Tenant> findByAddresseeId(AddresseeId id) {
        return Optional.ofNullable(this.cache.getIfPresent(id));
    }

    @Override
    public Stream<Tenant> findTenantsInDomains(Domains domains) {
        var allPresent = this.helperCache.getAllPresent(domains.value())
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return this.cache.getAllPresent(allPresent).values().stream();
    }

    @Override
    public Tenant index(Tenant tenant) {
        this.cache.put(tenant.id(), tenant);
        tenant.domains().value().forEach(domain -> {
            var ifPresent = this.helperCache.getIfPresent(domain);
            if (ifPresent == null) {
                this.helperCache.put(domain, Set.of(tenant.id()));
            } else {
                ifPresent.add(tenant.id());
            }
        });
        return tenant;
    }
}

package pt.sensae.services.notification.dispatcher.backend.application.addressee;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.dispatcher.backend.domain.adressee.AddresseeRepository;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AddresseeCacheImpl implements AddresseeRepository {

    private final Cache<AddresseeId, Addressee> cache;
    private boolean isSynced;

    public AddresseeCacheImpl() {
        this.cache = Caffeine.newBuilder().build();
        this.isSynced = false;
    }

    @Override
    public Addressee findById(AddresseeId id) {
        return this.cache.getIfPresent(id);
    }

    @Override
    public void index(Addressee addressee) {
        this.cache.put(addressee.id(), addressee);
    }

    @Override
    public void refresh(Set<Addressee> adrAddressees) {
        if (this.isSynced) {
            return;
        }
        var collect = adrAddressees.stream()
                .collect(Collectors.toMap(Addressee::id, Function.identity()));

        this.cache.invalidateAll();
        this.cache.putAll(collect);
        this.isSynced = true;
    }
}

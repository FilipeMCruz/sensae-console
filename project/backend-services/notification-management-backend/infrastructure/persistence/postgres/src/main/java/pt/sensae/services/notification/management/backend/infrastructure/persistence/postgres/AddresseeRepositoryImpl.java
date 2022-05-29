package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.addressee.AddresseeMapper;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository.AddresseeRepositoryPostgres;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class AddresseeRepositoryImpl implements AddresseeRepository {

    private final AddresseeRepositoryPostgres repositoryPostgres;

    public AddresseeRepositoryImpl(AddresseeRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    @Cacheable(value = "addressee_cache", key = "id")
    public Addressee findById(AddresseeId id) {
        var addresseePostgres = repositoryPostgres.findAllById(id.value().toString());
        return AddresseeMapper.daoToModel(addresseePostgres).findFirst().orElse(Addressee.of(id, new HashSet<>()));
    }

    @Override
    @Transactional
    @CachePut(value = "addressee_cache", key = "addressee.id()")
    public Addressee index(Addressee addressee) {
        repositoryPostgres.deleteAllById(addressee.id().toString());

        var addresseePostgres = repositoryPostgres.saveAll(AddresseeMapper.modelToDao(addressee)
                .collect(Collectors.toSet()));

        return AddresseeMapper.daoToModel(StreamSupport.stream(addresseePostgres.spliterator(), false))
                .findFirst().orElse(Addressee.of(addressee.id(), new HashSet<>()));
    }
}

package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.notification.management.backend.domain.adressee.Addressee;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeRepository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.mapper.addressee.AddresseeMapper;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.addressee.AddresseePostgres;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository.AddresseeRepositoryPostgres;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class AddresseeRepositoryImpl implements AddresseeRepository {

    private final AddresseeRepositoryPostgres repositoryPostgres;

    public AddresseeRepositoryImpl(AddresseeRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    public Addressee findById(AddresseeId id) {
        var addresseePostgres = repositoryPostgres.findAllById(id.value().toString());
        return AddresseeMapper.daoToModel(addresseePostgres).findFirst().orElse(Addressee.of(id, new HashSet<>()));
    }

    @Override
    public Stream<Addressee> findAll() {
        return AddresseeMapper.daoToModel(StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false));
    }

    @Override
    @Transactional
    public Addressee index(Addressee addressee) {
        repositoryPostgres.deleteById(addressee.id().value().toString());

        var addresseePostgres = repositoryPostgres.saveAll(AddresseeMapper.modelToDao(addressee)
                .collect(Collectors.toSet()));

        return AddresseeMapper.daoToModel(StreamSupport.stream(addresseePostgres.spliterator(), false))
                .findFirst().orElse(Addressee.of(addressee.id(), new HashSet<>()));
    }

    @Override
    public void update(Set<Addressee> configUpdates) {
        var updates = configUpdates.stream().flatMap(AddresseeMapper::modelToDao).collect(Collectors.toSet());
        repositoryPostgres.saveAll(updates);
    }
}

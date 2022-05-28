package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.addressee.AddresseePostgres;

import java.util.stream.Stream;

@Repository
public interface AddresseeRepositoryPostgres extends CrudRepository<AddresseePostgres, Long> {

    Stream<AddresseePostgres> findAllById(String addresseeId);

    void deleteAllById(String addresseeId);
}

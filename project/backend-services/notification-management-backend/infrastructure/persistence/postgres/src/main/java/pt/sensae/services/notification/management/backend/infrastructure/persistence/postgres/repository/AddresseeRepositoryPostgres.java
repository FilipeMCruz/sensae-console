package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.addressee.AddresseePostgres;

import java.util.stream.Stream;

@Repository
public interface AddresseeRepositoryPostgres extends CrudRepository<AddresseePostgres, Long> {

    Stream<AddresseePostgres> findAllById(String addresseeId);

    @Modifying
    @Query(value = "delete from addressee_config where id = :addresseeId")
    void deleteById(@Param("addresseeId") String addresseeId);
}

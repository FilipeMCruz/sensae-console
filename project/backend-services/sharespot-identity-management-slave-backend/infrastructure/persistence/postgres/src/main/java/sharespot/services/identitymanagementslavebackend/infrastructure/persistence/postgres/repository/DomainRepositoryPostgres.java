package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.domain.DomainPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepositoryPostgres extends CrudRepository<DomainPostgres, Long> {

    List<DomainPostgres> findAllByOidIn(List<String> domainId);

    @Query(value = "SELECT * FROM domain WHERE array_length(path,1) <= 1", nativeQuery = true)
    Optional<DomainPostgres> findRoot();
}

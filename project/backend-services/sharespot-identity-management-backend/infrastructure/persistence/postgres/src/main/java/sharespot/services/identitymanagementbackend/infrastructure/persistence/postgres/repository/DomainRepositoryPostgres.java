package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.domain.DomainPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepositoryPostgres extends CrudRepository<DomainPostgres, Long> {

    Optional<DomainPostgres> findByOid(String domainId);

    @Query(value = "SELECT * FROM domain WHERE array_length(path,1) <= 1", nativeQuery = true)
    Optional<DomainPostgres> findRoot();

    @Query(value = "SELECT * FROM domain WHERE array_length(path,1) <= 2 AND name = 'unallocated'", nativeQuery = true)
    Optional<DomainPostgres> findRootUnallocated();

    @Query(value = "SELECT * FROM domain WHERE oid in Cast(:domains AS text[])", nativeQuery = true)
    List<DomainPostgres> findDomainParents(@Param("domains") String domains);

    @Query(value = "SELECT * FROM domain WHERE path && Cast(:domainId AS text[])", nativeQuery = true)
    List<DomainPostgres> findDomainChilds(@Param("domainId") String domainId);

    @Query(value = "DELETE FROM domain WHERE path && Cast(:domainId AS text[])", nativeQuery = true)
    void deleteDomainChilds(@Param("domainId") String domainId);

    @Query(value = "UPDATE domain SET path = Cast(:newpath AS text[]) || path[:domainId:array_length(path,1)] WHERE path && Cast(:domainPath AS text[])", nativeQuery = true)
    void moveDomain(@Param("domainId") String domainId, @Param("domainPath") String domainPath, @Param("newpath") String path);
}

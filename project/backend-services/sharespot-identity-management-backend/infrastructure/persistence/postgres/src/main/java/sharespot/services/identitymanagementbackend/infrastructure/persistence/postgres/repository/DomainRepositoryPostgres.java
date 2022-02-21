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

    @Query(value = "SELECT * FROM domain WHERE array_length(path,1) <= 1")
    Optional<DomainPostgres> findRoot();

    @Query(value = "SELECT * FROM domain WHERE array_length(path,1) <= 2 AND name EQUAL 'unallocated'")
    Optional<DomainPostgres> findRootUnallocated();

    @Query(value = "SELECT * FROM domain WHERE path && ARRAY[:domainId]")
    List<DomainPostgres> findDomainParents(@Param("domainId") String domainId);

    @Query(value = "SELECT * FROM domain WHERE oid in :path")
    List<DomainPostgres> findDomainChilds(@Param("path") String[] path);
    
    @Query(value = "DELETE FROM domain WHERE oid in :path")
    void deleteDomainChilds(@Param("path") String[] path);

    @Query(value = "UPDATE domain SET path = ARRAY[:newpath] || path[:domainId:array_length(path,1)] WHERE path && ARRAY[:domainId]")
    void moveDomain(@Param("domainId") String domainId, @Param("newpath") String[] path);
}

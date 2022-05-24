package pt.sensae.services.rule.management.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.model.RuleScenarioPostgres;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface RuleScenarioRepositoryPostgres extends CrudRepository<RuleScenarioPostgres, Long> {

    Optional<RuleScenarioPostgres> findByScenarioId(String scenarioId);
    
    Stream<RuleScenarioPostgres> findByAppliedFalse();

    @Query(value = "SELECT * FROM rule WHERE Cast(owners AS text[]) && Cast(:domains AS text[])", nativeQuery = true)
    Stream<RuleScenarioPostgres> findScenarioOwned(@Param("domains") String domains);
}

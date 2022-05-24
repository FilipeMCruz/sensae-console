package pt.sensae.services.rule.management.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.rule.management.domain.RuleScenarioOwners;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.mapper.RuleScenarioMapper;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioRepository;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.model.RuleScenarioPostgres;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.repository.RuleScenarioRepositoryPostgres;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.repository.util.PostgresArrayMapper;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class RuleScenarioRepositoryImpl implements RuleScenarioRepository {

    private final RuleScenarioRepositoryPostgres repositoryPostgres;

    public RuleScenarioRepositoryImpl(RuleScenarioRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    public RuleScenario save(RuleScenario domain) {
        var scenarioPostgres = RuleScenarioMapper.domainToPostgres(domain);

        var scenarioOpt = repositoryPostgres.findByScenarioId(domain.id().getValue());
        if (scenarioOpt.isPresent()) {
            var old = scenarioOpt.get();
            old.scenarioContent = scenarioPostgres.scenarioContent;
            old.applied = scenarioPostgres.applied;
            old.deleted = false;
            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(scenarioPostgres);
        }

        return domain;
    }

    @Override
    public Stream<RuleScenario> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .filter(rule -> !rule.deleted)
                .map(RuleScenarioMapper::postgresToDomain);
    }

    @Override
    @Transactional
    public RuleScenarioId delete(RuleScenarioId id) {
        var scenarioOpt = repositoryPostgres.findByScenarioId(id.getValue());
        scenarioOpt.ifPresent(scenario -> {
            scenario.deleted = true;
            scenario.applied = false;
            repositoryPostgres.save(scenario);
        });
        return id;
    }

    @Override
    public Optional<RuleScenario> findById(RuleScenarioId id) {
        return repositoryPostgres.findByScenarioId(id.getValue())
                .filter(rule -> !rule.deleted)
                .map(RuleScenarioMapper::postgresToDomain);
    }

    @Override
    public Stream<RuleScenario> findOwned(RuleScenarioOwners owners) {
        var ownersString = PostgresArrayMapper.toArray(owners.domains()
                .stream()
                .map(UUID::toString)
                .collect(Collectors.toList()));
        return repositoryPostgres.findScenarioOwned(ownersString)
                .filter(rule -> !rule.deleted)
                .map(RuleScenarioMapper::postgresToDomain);
    }

    @Override
    public Stream<RuleScenario> findDrafts() {
        var oldDrafts = repositoryPostgres.findByAppliedFalse()
                .peek(scenario -> scenario.applied = true)
                .collect(Collectors.toSet());
        repositoryPostgres.saveAll(oldDrafts);
        return oldDrafts.stream().map(RuleScenarioMapper::postgresToDomain);
    }
}

package pt.sensae.services.rule.management.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.rule.management.domain.RuleScenarioOwners;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.mapper.RuleScenarioMapper;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioRepository;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.repository.RuleScenarioRepositoryPostgres;

import java.util.Optional;
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
        var transformationPostgres = RuleScenarioMapper.domainToPostgres(domain);

        var scenarioOpt = repositoryPostgres.findByScenarioId(domain.id().getValue());
        if (scenarioOpt.isPresent()) {
            var old = scenarioOpt.get();
            old.scenarioContent = transformationPostgres.scenarioContent;
            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(transformationPostgres);
        }

        return domain;
    }

    @Override
    public Stream<RuleScenario> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(RuleScenarioMapper::postgresToDomain);
    }

    @Override
    @Transactional
    public RuleScenarioId delete(RuleScenarioId id) {
        repositoryPostgres.deleteByScenarioId(id.getValue());
        return id;
    }

    @Override
    public Optional<RuleScenario> findById(RuleScenarioId id) {
        return repositoryPostgres.findByScenarioId(id.getValue())
                .map(RuleScenarioMapper::postgresToDomain);
    }

    @Override
    public Stream<RuleScenario> findOwned(RuleScenarioOwners owners) {
        return null;
    }
}

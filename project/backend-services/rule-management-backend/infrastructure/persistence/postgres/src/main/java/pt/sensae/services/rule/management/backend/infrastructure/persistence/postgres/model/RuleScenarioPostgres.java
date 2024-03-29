package pt.sensae.services.rule.management.backend.infrastructure.persistence.postgres.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "rule")
public class RuleScenarioPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(name = "id", unique = true)
    public String scenarioId;

    @Column(name = "applied")
    public boolean applied;

    @Column(name = "deleted")
    public boolean deleted;

    @Column(name = "content", columnDefinition = "TEXT")
    public String scenarioContent;

    @Column(name = "owners", columnDefinition = "text[]")
    @Type(type = "pt.sensae.services.rule.management.backend.infrastructure.persistence.postgres.repository.util.GenericArrayUserType")
    public String[] scenarioOwners;
}

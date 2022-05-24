package pt.sensae.services.rule.management.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.domain.RuleScenarioOwners;
import pt.sensae.services.rule.management.infrastructure.endpoint.graphql.model.RuleScenarioIdDTOImpl;
import pt.sensae.services.rule.management.infrastructure.endpoint.graphql.model.RuleScenarioDTOImpl;
import pt.sensae.services.rule.management.application.RuleScenarioDTO;
import pt.sensae.services.rule.management.application.RuleScenarioMapper;
import pt.sensae.services.rule.management.application.RuleScenarioIdDTO;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.domain.RuleScenarioContent;

import java.util.Set;
import java.util.UUID;

@Service
public class RuleScenarioMapperImpl implements RuleScenarioMapper {

    @Override
    public RuleScenario dtoToDomain(RuleScenarioDTO dto, Set<UUID> owners) {
        var scenarioDTO = (RuleScenarioDTOImpl) dto;
        return new RuleScenario(dtoToDomain(scenarioDTO.id), RuleScenarioContent.of(scenarioDTO.content), RuleScenarioOwners.of(owners));
    }

    @Override
    public RuleScenarioDTO domainToDto(RuleScenario domain) {
        var dto = new RuleScenarioDTOImpl();
        var scenarioIdDTO = new RuleScenarioIdDTOImpl();
        scenarioIdDTO.value = domain.id().getValue();
        dto.id = scenarioIdDTO;
        dto.content = domain.content().value();
        return dto;
    }

    @Override
    public RuleScenarioId dtoToDomain(RuleScenarioIdDTO dto) {
        var scenarioIdDTO = (RuleScenarioIdDTOImpl) dto;
        return RuleScenarioId.of(scenarioIdDTO.value);
    }
}

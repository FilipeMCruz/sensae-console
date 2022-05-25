package pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domain.*;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.model.RuleScenarioIdDTOImpl;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.model.RuleScenarioDTOImpl;
import pt.sensae.services.rule.management.backend.application.RuleScenarioDTO;
import pt.sensae.services.rule.management.backend.application.RuleScenarioMapper;
import pt.sensae.services.rule.management.backend.application.RuleScenarioIdDTO;

import java.util.Set;
import java.util.UUID;

@Service
public class RuleScenarioMapperImpl implements RuleScenarioMapper {

    @Override
    public RuleScenario dtoToDomain(RuleScenarioDTO dto, Set<UUID> owners) {
        var scenarioDTO = (RuleScenarioDTOImpl) dto;
        return new RuleScenario(dtoToDomain(scenarioDTO.id), RuleScenarioContent.of(scenarioDTO.content), 
                RuleScenarioOwners.of(owners), RuleApplied.getInstance(), RuleDeleted.getInstance());
    }

    @Override
    public RuleScenarioDTO domainToDto(RuleScenario domain) {
        var dto = new RuleScenarioDTOImpl();
        var scenarioIdDTO = new RuleScenarioIdDTOImpl();
        scenarioIdDTO.value = domain.id().getValue();
        dto.id = scenarioIdDTO;
        dto.content = domain.content().value();
        dto.applied = domain.isApplied().value();
        return dto;
    }

    @Override
    public RuleScenarioId dtoToDomain(RuleScenarioIdDTO dto) {
        var scenarioIdDTO = (RuleScenarioIdDTOImpl) dto;
        return RuleScenarioId.of(scenarioIdDTO.value);
    }
}

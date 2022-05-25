import {RuleScenarioIdMapper} from './RuleScenarioIdMapper';
import {IsDraft, RuleScenario, RuleScenarioContent} from "@frontend-services/rule-management/model";
import {RuleScenarioDTO, RuleScenarioInputDTO} from "@frontend-services/rule-management/dto";

export class RuleScenarioMapper {
  static dtoToModel(dto: RuleScenarioDTO): RuleScenario {
    const ruleScenarioId = RuleScenarioIdMapper.dtoToModel(dto.id);
    const content = new RuleScenarioContent(dto.content);
    const isDraft = new IsDraft(!dto.applied);
    return new RuleScenario(ruleScenarioId, content, isDraft);
  }

  static modelToDto(model: RuleScenario): RuleScenarioInputDTO {
    return {id: {value: model.id.value}, content: model.content.value};
  }
}

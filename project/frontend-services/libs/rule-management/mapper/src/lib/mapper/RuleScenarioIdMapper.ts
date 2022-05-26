import {RuleScenarioIdDTO} from "@frontend-services/rule-management/dto";
import {RuleScenarioId} from "@frontend-services/rule-management/model";

export class RuleScenarioIdMapper {
  static dtoToModel(dto: RuleScenarioIdDTO): RuleScenarioId {
    return new RuleScenarioId(dto.value);
  }

  static modelToDto(model: RuleScenarioId): RuleScenarioIdDTO {
    return {value: model.value};
  }
}

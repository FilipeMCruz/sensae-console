import {RuleScenario} from "@frontend-services/rule-management/model";
import {RuleScenarioMapper} from "./RuleScenarioMapper";
import {RuleScenarioInput, RuleScenarioResult} from "@frontend-services/rule-management/dto";

export class RuleScenarioRegisterMapper {
  static modelToDto(model: RuleScenario): RuleScenarioInput {
    return {scenario: RuleScenarioMapper.modelToDto(model)};
  }

  static dtoToModel(dto: RuleScenarioResult): RuleScenario {
    return RuleScenarioMapper.dtoToModel(dto.index);
  }
}

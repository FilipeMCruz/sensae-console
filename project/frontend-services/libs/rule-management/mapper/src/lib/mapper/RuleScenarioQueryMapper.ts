import {RuleScenarioQuery} from "@frontend-services/rule-management/dto";
import {RuleScenario} from "@frontend-services/rule-management/model";
import {RuleScenarioMapper} from "./RuleScenarioMapper";

export class RuleScenarioQueryMapper {
  static dtoToModel(dto: RuleScenarioQuery): Array<RuleScenario> {
    return dto.scenario.map((e) =>
      RuleScenarioMapper.dtoToModel(e)
    );
  }
}

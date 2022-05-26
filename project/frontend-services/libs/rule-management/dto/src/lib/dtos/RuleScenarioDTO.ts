export interface RuleScenarioQuery {
  scenario: Array<RuleScenarioDTO>;
}

export interface RuleScenarioInput {
  scenario: RuleScenarioInputDTO;
}

export interface RuleScenarioResult {
  index: RuleScenarioDTO;
}

export interface RuleScenarioDelete {
  delete: RuleScenarioIdDTO;
}

export interface RuleScenarioDTO {
  id: RuleScenarioIdDTO;
  content: string;
  applied: boolean;
}

export interface RuleScenarioInputDTO {
  id: RuleScenarioIdDTO;
  content: string;
}

export interface RuleScenarioIdDTO {
  value: string;
}

import {RuleScenario} from "./RuleScenario";

export class RuleScenarioPair {
  constructor(
    public fresh: RuleScenario,
    public old: RuleScenario
  ) {
  }
}

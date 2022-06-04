import {RuleScenarioContent} from "./RuleScenarioContent";
import {RuleScenarioId} from "./RuleScenarioId";
import {IsDraft} from "./IsDraft";

export class RuleScenario {
  constructor(
    public id: RuleScenarioId,
    public content: RuleScenarioContent,
    public isDraft: IsDraft
  ) {
  }

  static empty() {
    return new RuleScenario(
      RuleScenarioId.empty(),
      RuleScenarioContent.empty(),
      IsDraft.default()
    );
  }

  copy() {
    return new RuleScenario(this.id.copy(), this.content.copy(), new IsDraft(true));
  }

  isValid() {
    return (
      this.id.value.trim().length !== 0 &&
      this.content.value.trim().length !== 0
    );
  }
}

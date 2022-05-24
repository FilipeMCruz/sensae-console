export class RuleScenarioContent {
  constructor(public value: string) {
  }

  static empty() {
    return new RuleScenarioContent('');
  }
}

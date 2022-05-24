export class RuleScenarioId {
  constructor(public value: string) {}

  static empty() {
    return new RuleScenarioId('');
  }
}

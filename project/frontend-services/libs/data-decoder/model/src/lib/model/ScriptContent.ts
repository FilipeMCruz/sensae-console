export class ScriptContent {
  constructor(public content: string) {
  }

  static empty() {
    return new ScriptContent('');
  }
}

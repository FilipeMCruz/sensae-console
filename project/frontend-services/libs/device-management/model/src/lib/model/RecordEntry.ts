export class RecordEntry {
  constructor(
    public label: string,
    public content: string,
  ) {
  }

  static empty() {
    return new RecordEntry('', '');
  }

  clone(): RecordEntry {
    return new RecordEntry(this.label, this.content);
  }

  isValid(): boolean {
    return this.label.trim().length !== 0 && this.content.trim().length !== 0;
  }
}

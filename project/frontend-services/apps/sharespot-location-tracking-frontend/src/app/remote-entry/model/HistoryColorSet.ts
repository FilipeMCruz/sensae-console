export class HistoryColorSet {
  constructor(public index: number, public activeColor: string, public unknownActiveColor: string) {
  }

  valid(i: number) {
    return i % 8 == this.index;
  }

}

export class HistoryColorSetPicker {

  static generateColorSet() {
    const colorSets: Array<HistoryColorSet> = [];
    colorSets.push(new HistoryColorSet(0, '#01497c', '#a9d6e5'));
    colorSets.push(new HistoryColorSet(1, '#718355', '#b5c99a'));
    colorSets.push(new HistoryColorSet(2, '#689d6a', '#8ec07c'));
    colorSets.push(new HistoryColorSet(3, '#d79921', '#fabd2f'));
    colorSets.push(new HistoryColorSet(4, '#d65d06', '#fe8019'));
    colorSets.push(new HistoryColorSet(5, '#7f5539', '#ddb892'));
    colorSets.push(new HistoryColorSet(6, '#815ac0', '#d2b7e5'));
    colorSets.push(new HistoryColorSet(7, '#ff85a1', '#f9bec7'));
    return colorSets;
  }
}

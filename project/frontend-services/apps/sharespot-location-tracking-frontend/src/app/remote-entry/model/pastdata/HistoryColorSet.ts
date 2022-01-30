export class HistoryColorSet {
  constructor(public activeColor: string, public unknownActiveColor: string) {
  }

  static get(i: number): HistoryColorSet {
    const colorIndex = i % 8;
    switch (colorIndex) {
      case 0: {
        return new HistoryColorSet('#01497c', '#a9d6e5');
      }
      case 1: {
        return new HistoryColorSet('#718355', '#b5c99a');
      }
      case 2: {
        return new HistoryColorSet('#ff85a1', '#f9bec7');
      }
      case 3: {
        return new HistoryColorSet('#d79921', '#fabd2f');
      }
      case 4: {
        return new HistoryColorSet('#d65d06', '#fe8019');
      }
      case 5: {
        return new HistoryColorSet('#7f5539', '#ddb892');
      }
      case 6: {
        return new HistoryColorSet('#815ac0', '#d2b7e5');
      }
      case 7: {
        return new HistoryColorSet('#689d6a', '#8ec07c');
      }
      default : {
        return new HistoryColorSet('#01497c', '#a9d6e5');
      }
    }
  }
}

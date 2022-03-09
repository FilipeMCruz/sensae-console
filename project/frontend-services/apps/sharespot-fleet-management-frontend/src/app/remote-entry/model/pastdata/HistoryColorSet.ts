import { DeviceHistorySegmentType } from './DeviceHistorySegment';

export class HistoryColorSet {
  constructor(
    public activeColor: string,
    public unknownActiveColor: string,
    public inactiveColor: string,
    public unknownInactiveColor: string
  ) {}

  static get(i: number): HistoryColorSet {
    const colorIndex = i % 8;
    switch (colorIndex) {
      case 0: {
        return new HistoryColorSet('#01497c', '#a9d6e5', '#f34044', '#f34044');
      }
      case 1: {
        return new HistoryColorSet('#718355', '#b5c99a', '#f34044', '#f34044');
      }
      case 2: {
        return new HistoryColorSet('#ff85a1', '#f9bec7', '#f34044', '#f34044');
      }
      case 3: {
        return new HistoryColorSet('#d79921', '#fabd2f', '#f34044', '#f34044');
      }
      case 4: {
        return new HistoryColorSet('#d65d06', '#fe8019', '#f34044', '#f34044');
      }
      case 5: {
        return new HistoryColorSet('#7f5539', '#ddb892', '#f34044', '#f34044');
      }
      case 6: {
        return new HistoryColorSet('#815ac0', '#d2b7e5', '#f34044', '#f34044');
      }
      case 7: {
        return new HistoryColorSet('#689d6a', '#8ec07c', '#f34044', '#f34044');
      }
      default: {
        return new HistoryColorSet('#01497c', '#a9d6e5', '#f34044', '#f34044');
      }
    }
  }

  pickColor(type: DeviceHistorySegmentType) {
    if (type === DeviceHistorySegmentType.UNKNOWN_ACTIVE) {
      return this.unknownActiveColor;
    } else if (type === DeviceHistorySegmentType.ACTIVE) {
      return this.activeColor;
    } else if (type === DeviceHistorySegmentType.INACTIVE) {
      return this.inactiveColor;
    } else {
      return this.unknownInactiveColor;
    }
  }
}

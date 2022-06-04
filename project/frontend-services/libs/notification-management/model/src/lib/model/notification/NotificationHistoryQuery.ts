export class NotificationHistoryQuery {
  constructor(public start: Date, public end: Date) {
  }

  static lastMonth() {
    const current = new Date();
    const start = new Date();
    start.setMonth(start.getMonth() - 1);
    return new NotificationHistoryQuery(start, current);
  }
}

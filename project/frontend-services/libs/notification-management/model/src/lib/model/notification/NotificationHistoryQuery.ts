export class NotificationHistoryQuery {
  constructor(public start: Date, public end: Date) {
  }

  static lastMonth() {
    const current = new Date();
    const start = new Date();
    start.setMonth(start.getMonth() - 1);
    return new NotificationHistoryQuery(start, current);
  }

  static lastWeek() {
    const current = new Date();
    const start = new Date();
    start.setDate(start.getDate() - 7);
    return new NotificationHistoryQuery(start, current);
  }

  static from(start: Date, end: Date) {
    return new NotificationHistoryQuery(start, end);
  }
}

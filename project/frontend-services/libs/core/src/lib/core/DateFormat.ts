export class DateFormat {
  public static timeAgo(reportedDate: Date): string {
    const seconds = Math.floor((new Date().valueOf() - reportedDate.valueOf()) / 1000);

    let interval = seconds / 31536000;

    if (interval > 1) {
      const floor = Math.floor(interval);
      return floor == 1 ? floor + " year" : floor + " years";
    }
    interval = seconds / 2592000;
    if (interval > 1) {
      const floor = Math.floor(interval);
      return floor == 1 ? floor + " month" : floor + " months";
    }
    interval = seconds / 86400;
    if (interval > 1) {
      const floor = Math.floor(interval);
      return floor == 1 ? floor + " day" : floor + " days";
    }
    interval = seconds / 3600;
    if (interval > 1) {
      const floor = Math.floor(interval);
      return floor == 1 ? floor + " hour" : floor + " hours";
    }
    interval = seconds / 60;
    if (interval > 1) {
      const floor = Math.floor(interval);
      return floor == 1 ? floor + " minute" : floor + " minutes";
    }
    const floor = Math.floor(seconds);
    return floor == 1 ? floor + " second" : floor + " seconds";
  }
}

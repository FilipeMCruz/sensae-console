import {ContentType} from "./ContentType";

export class Notification {

  static empty() {
    return new Notification("", new Date(), ContentType.empty(), "");
  }

  constructor(public id: string, public reportedAt: Date, public contentType: ContentType, public description: string) {
  }

  public timeAgo(): string {
    const seconds = Math.floor((new Date().valueOf() - this.reportedAt.valueOf()) / 1000);

    let interval = seconds / 31536000;

    if (interval > 1) {
      return Math.floor(interval) + " years";
    }
    interval = seconds / 2592000;
    if (interval > 1) {
      return Math.floor(interval) + " months";
    }
    interval = seconds / 86400;
    if (interval > 1) {
      return Math.floor(interval) + " days";
    }
    interval = seconds / 3600;
    if (interval > 1) {
      return Math.floor(interval) + " hours";
    }
    interval = seconds / 60;
    if (interval > 1) {
      return Math.floor(interval) + " minutes";
    }
    return Math.floor(seconds) + " seconds";
  }

  isEmpty(): boolean {
    return this.id == "";
  }

  public toSnackBar(): string {
    return "New notification!!!" + "\nCategory: " + this.contentType.getCategory() + "\nSub Category: " + this.contentType.getSubCategory()
  }
}

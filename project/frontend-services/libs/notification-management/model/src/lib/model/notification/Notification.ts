import {ContentType} from "./ContentType";
import {Reader} from "./Reader";
import {DateFormat} from "@frontend-services/core";

export class Notification {

  static empty() {
    return new Notification("", new Date(), ContentType.empty(), "", []);
  }

  constructor(public id: string, public reportedAt: Date, public contentType: ContentType, public description: string, public readers: Array<Reader>) {
  }

  public timeAgo(): string {
    return DateFormat.timeAgo(this.reportedAt);
  }

  public getReaders(): string {
    if (this.readers.length === 0) {
      return "Unread";
    } else if (this.readers.length > 2) {
      return "Read by: " + this.readers.slice(0, 2).map(r => r.name).join(" and ") + " and others";
    }
    return "Read by: " + this.readers.map(r => r.name).join(" and ");
  }

  isRead() {
    return this.readers.length != 0;
  }

  isEmpty(): boolean {
    return this.id == "";
  }

  public toSnackBar(): string {
    return "New notification!!!" + "\nCategory: " + this.contentType.getCategory() + "\nSub Category: " + this.contentType.getSubCategory()
  }

  wasReadBy(oid: string) {
    return this.readers.some(s => s.oid === oid);
  }
}

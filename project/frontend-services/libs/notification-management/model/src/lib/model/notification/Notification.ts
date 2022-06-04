import {ContentType} from "./ContentType";
import {DateFormat} from "@frontend-services/core";

export class Notification {

  static empty() {
    return new Notification("", new Date(), ContentType.empty(), "");
  }

  constructor(public id: string, public reportedAt: Date, public contentType: ContentType, public description: string) {
  }

  public timeAgo(): string {
    return DateFormat.timeAgo(this.reportedAt);
  }

  isEmpty(): boolean {
    return this.id == "";
  }

  public toSnackBar(): string {
    return "New notification!!!" + "\nCategory: " + this.contentType.getCategory() + "\nSub Category: " + this.contentType.getSubCategory()
  }
}

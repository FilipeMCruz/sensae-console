import {ContentType} from "./ContentType";

export class Notification {
  constructor(public id: string, public reportedAt: Date, public contentType: ContentType, public description: string) {
  }
}

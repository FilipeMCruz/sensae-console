import {NotificationSeverityLevel} from "./NotificationSeverityLevel";

export class ContentType {
  constructor(public category: string, public subCategory: string, public severity: NotificationSeverityLevel) {
  }
}

import {NotificationSeverityLevel} from "./NotificationSeverityLevel";

export class ContentType {
  constructor(public category: string, public subCategory: string, public severity: NotificationSeverityLevel) {
  }

  static empty() {
    return new ContentType("", "", NotificationSeverityLevel.WARNING);
  }

  public getCategory() {
    const result = this.category.replace(/([A-Z])/g, " $1");
    return result.charAt(0).toUpperCase() + result.slice(1);
  }

  public getSubCategory() {
    const result = this.subCategory.replace(/([A-Z])/g, " $1");
    return result.charAt(0).toUpperCase() + result.slice(1);
  }

  public getSeverityAsNumber(): number {
    switch (this.severity) {
      case NotificationSeverityLevel.CRITICAL:
        return 5;
      case NotificationSeverityLevel.WARNING:
        return 4;
      case NotificationSeverityLevel.ADVISORY:
        return 3;
      case NotificationSeverityLevel.WATCH:
        return 2;
      case NotificationSeverityLevel.INFORMATION:
        return 1;
    }
  }

  public getSeverityAsColor(): string {
    switch (this.severity) {
      case NotificationSeverityLevel.CRITICAL:
        return "#E03C32";
      case NotificationSeverityLevel.WARNING:
        return "#FF8C01";
      case NotificationSeverityLevel.ADVISORY:
        return "#FFD301";
      case NotificationSeverityLevel.WATCH:
        return "#7BB662";
      case NotificationSeverityLevel.INFORMATION:
        return "#006B3E";
    }
  }

  equals(data: ContentType) {
    return this.category === data.category && this.subCategory === data.subCategory && this.severity === data.severity;
  }
}

import {Device} from "./Device";
import {DeviceDataDetails} from "./DeviceDataDetails";

export class DeviceData {
  constructor(public dataId: string, public device: Device, public reportedAt: Date, public data: DeviceDataDetails) {
  }

  generatePopupText(): string {
    return "<strong>Device Name:</strong> " + this.device.name +
      "<br><strong>Device Id:</strong> " + this.device.id +
      "<br><strong>Time received at:</strong> " + this.reportedAt.toTimeString().split(' ')[0] +
      "<br><strong>Date received at:</strong> " + this.reportedAt.toJSON().substring(0, 10) +
      this.device.records.map(e => "<br><strong>" + e.label + ":</strong> " + e.content)
  }
}

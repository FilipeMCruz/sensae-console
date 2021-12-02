import {SensorCoordinates} from "./SensorCoordinates";
import {RecordEntry} from "./RecordEntry";
import {Sensor} from "./Sensor";

export class GPSSensorData {
  constructor(public dataId: string, public device: Sensor, public reportedAt: Date, public coordinates: SensorCoordinates, public record: Array<RecordEntry>) {
  }

  generatePopupText(): string {
    return "<strong>Device Name:</strong> " + this.device.name +
      "<br><strong>Time received at:</strong> " + this.reportedAt.toTimeString().split(' ')[0] +
      "<br><strong>Date received at:</strong> " + this.reportedAt.toJSON().substring(0, 10) +
      this.record.map(e => "<br><strong>" + e.label + ":</strong> " + e.content)
  }
}

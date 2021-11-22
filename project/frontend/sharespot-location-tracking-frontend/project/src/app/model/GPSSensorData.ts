import {SensorCoordinates} from "./SensorCoordinates";
import {RecordEntry} from "./RecordEntry";

export class GPSSensorData {
  constructor(public dataId: String, public deviceId: String, public reportedAt: Date, public coordinates: SensorCoordinates, public record: Array<RecordEntry>) {
  }

  generatePopupText(): string {
    return "<strong>DeviceID:</strong> " + this.deviceId +
      "<br><strong>Time received at:</strong> " + this.reportedAt.toTimeString().split(' ')[0] +
      "<br><strong>Date received at:</strong> " + this.reportedAt.toJSON().substring(0, 10) +
      this.record.map(e => "<br><strong>" + e.label + ":</strong> " + e.content)
  }
}

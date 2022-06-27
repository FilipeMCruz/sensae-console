import {ScriptContent} from "./ScriptContent";
import {SensorTypeId} from "./SensorTypeId";

export class DataDecoder {
  constructor(
    public data: SensorTypeId,
    public script: ScriptContent,
    public lastTimeSeen: Date
  ) {
  }

  static empty() {
    const d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setUTCMilliseconds(0);
    return new DataDecoder(
      SensorTypeId.empty(),
      ScriptContent.empty(),
      d
    );
  }

  isValid() {
    return (
      this.data.isValid() &&
      this.script.content.trim().length !== 0
    );
  }
}

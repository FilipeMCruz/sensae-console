import {ScriptContent} from "./ScriptContent";
import {SensorTypeId} from "./SensorTypeId";

export class DataDecoder {
  constructor(
    public data: SensorTypeId,
    public script: ScriptContent
  ) {
  }

  static empty() {
    return new DataDecoder(
      SensorTypeId.empty(),
      ScriptContent.empty()
    );
  }

  isValid() {
    return (
      this.data.isValid() &&
      this.script.content.trim().length !== 0
    );
  }
}

import { Component } from '@angular/core';
import { SensorDataRecordLabel } from '../../model/SensorDataRecordLabel';
import { RecordType } from '../../model/RecordType';
import { RecordEntry } from '../../model/RecordEntry';

@Component({
  selector: 'frontend-services-new-record-entry',
  templateUrl: './new-record-entry.component.html',
  styleUrls: ['./new-record-entry.component.scss']
})
export class NewRecordEntryComponent {

  type = 'Basic';

  basicLabel!: string;
  sensorDataLabel!: string;
  content!: string;

  sensorDataType: Array<string> = Object.values(SensorDataRecordLabel);
  recordType: Array<string> = Object.values(RecordType);

  option = '';

  isTypeBasic() {
    return this.type === RecordType.BASIC.toString();
  }

  addEntry() {
    let entry;
    if (this.isTypeBasic()) {
      entry = new RecordEntry(this.basicLabel, this.content, RecordType.BASIC);
    } else {
      entry = new RecordEntry(this.sensorDataLabel as SensorDataRecordLabel, this.content, RecordType.SENSOR_DATA);
    }
    console.log(entry);
  }
}

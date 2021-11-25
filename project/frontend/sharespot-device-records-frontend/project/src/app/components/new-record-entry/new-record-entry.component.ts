import {Component, OnInit} from '@angular/core';
import {SensorDataRecordLabel} from "../../model/SensorDataRecordLabel";
import {RecordType} from "../../model/RecordType";
import {RecordEntry} from "../../model/RecordEntry";

@Component({
  selector: 'app-new-record-entry',
  templateUrl: './new-record-entry.component.html',
  styleUrls: ['./new-record-entry.component.css']
})
export class NewRecordEntryComponent implements OnInit {

  type: string = "Basic";

  basicLabel!: string;
  sensorDataLabel!: string;
  content!: string;

  sensorDataType: Array<string> = Object.values(SensorDataRecordLabel);
  recordType: Array<string> = Object.values(RecordType);

  option: string = "";

  constructor() {
  }

  ngOnInit(): void {
  }

  isTypeBasic() {
    return this.type === RecordType.BASIC.toString();
  }

  addEntry() {
    let entry;
    if (this.isTypeBasic()) {
      entry = new RecordEntry(this.basicLabel, this.content, RecordType.BASIC)
    } else {
      entry = new RecordEntry(this.sensorDataLabel as SensorDataRecordLabel, this.content, RecordType.SENSOR_DATA)
    }
    console.log(entry);
  }
}

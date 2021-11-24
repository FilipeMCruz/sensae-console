import {Component, OnInit} from '@angular/core';
import {SensorDataRecordLabel} from "../../model/SensorDataRecordLabel";
import {RecordType} from "../../model/RecordType";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-new-record-entry',
  templateUrl: './new-record-entry.component.html',
  styleUrls: ['./new-record-entry.component.css']
})
export class NewRecordEntryComponent implements OnInit {

  type: string = "Basic";
  control = new FormControl();

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
    console.log(this.type, this.control, )
  }

}

import {Component, Input, OnInit} from '@angular/core';
import {DeviceRecord} from "../../model/DeviceRecord";

@Component({
  selector: 'app-old-device-record',
  templateUrl: './old-device-record.component.html',
  styleUrls: ['./old-device-record.component.css']
})
export class OldDeviceRecordComponent implements OnInit {

  @Input()
  device: DeviceRecord | undefined;

  constructor() {
  }

  ngOnInit(): void {
  }

}

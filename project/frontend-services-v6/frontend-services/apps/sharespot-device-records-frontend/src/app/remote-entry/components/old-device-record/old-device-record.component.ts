import { Component, Input } from '@angular/core';
import { DeviceRecord } from '../../model/DeviceRecord';

@Component({
  selector: 'frontend-services-old-device-record',
  templateUrl: './old-device-record.component.html',
  styleUrls: ['./old-device-record.component.scss']
})
export class OldDeviceRecordComponent {

  @Input()
  sensor: DeviceRecord | undefined;

}

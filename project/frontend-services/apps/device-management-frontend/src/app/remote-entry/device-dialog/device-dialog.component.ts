import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {
  DeviceInformationPair,
  DeviceViewType,
} from '@frontend-services/device-management/model';

@Component({
  selector: 'frontend-services-device-record-dialog',
  templateUrl: 'device-dialog.component.html',
  styleUrls: ['./device-dialog.component.scss'],
})
export class DeviceDialogComponent {
  deviceViewType = DeviceViewType;

  constructor(
    public dialogRef: MatDialogRef<DeviceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeviceInformationPair
  ) {
  }
}

import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  DeviceRecordPair,
  DeviceViewType,
} from '@frontend-services/device-records-model';

@Component({
  selector: 'frontend-services-device-record-dialog',
  templateUrl: 'device-record-dialog.component.html',
  styleUrls: ['./device-record-dialog.component.scss'],
})
export class DeviceRecordDialogComponent {
  deviceViewType = DeviceViewType;

  constructor(
    public dialogRef: MatDialogRef<DeviceRecordDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeviceRecordPair
  ) {}
}

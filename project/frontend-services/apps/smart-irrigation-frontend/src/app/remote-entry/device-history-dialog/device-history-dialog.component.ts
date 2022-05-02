import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {SensorDataHistory} from "@frontend-services/smart-irrigation/model";

@Component({
  selector: 'frontend-services-device-history-dialog',
  templateUrl: 'device-history-dialog.component.html',
  styleUrls: ['./device-history-dialog.component.scss'],
})
export class DeviceHistoryDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DeviceHistoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SensorDataHistory,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

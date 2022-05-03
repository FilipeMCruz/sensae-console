import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Data, HistoryQueryFilters, SensorDataHistory} from "@frontend-services/smart-irrigation/model";
import {FetchHistory} from "@frontend-services/smart-irrigation/services";

@Component({
  selector: 'frontend-services-device-history-dialog',
  templateUrl: 'device-history-dialog.component.html',
  styleUrls: ['./device-history-dialog.component.scss'],
})
export class DeviceHistoryDialogComponent implements OnInit {


  history: SensorDataHistory[] = [];

  constructor(
    private fetchHistoryService: FetchHistory,
    public dialogRef: MatDialogRef<DeviceHistoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Data,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.fetchHistoryService.getData(HistoryQueryFilters.defaultDevice(this.data.device.id))
      .subscribe(
        next => this.history = next
      );
  }
}

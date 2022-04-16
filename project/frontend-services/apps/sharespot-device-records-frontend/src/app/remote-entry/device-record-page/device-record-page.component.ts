import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {DeviceRecordDialogComponent} from '../device-record-dialog/device-record-dialog.component';
import {DeviceRecord, DeviceRecordPair, DeviceViewType,} from '@frontend-services/device-records/model';
import {DeleteDeviceRecord, GetAllDeviceRecords, IndexDeviceRecord,} from '@frontend-services/device-records/services';
import {AuthService} from "@frontend-services/simple-auth-lib";

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './device-record-page.component.html',
  styleUrls: ['./device-record-page.component.scss'],
})
export class DeviceRecordPageComponent implements OnInit {
  records: Array<DeviceRecord> = new Array<DeviceRecord>();

  deviceViewType = DeviceViewType;

  constructor(
    public dialog: MatDialog,
    private recordsCollector: GetAllDeviceRecords,
    private indexer: IndexDeviceRecord,
    private eraser: DeleteDeviceRecord,
    private authService: AuthService
  ) {
  }

  openDialog(data: DeviceRecordPair) {
    const dialogRef = this.dialog.open(DeviceRecordDialogComponent, {
      width: '1400px',
      data,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.saveItem(data.fresh);
      }
    });
  }

  ngOnInit(): void {
    this.fetchAllDevices();
  }

  fetchAllDevices() {
    this.recordsCollector
      .getData()
      .subscribe((data: Array<DeviceRecord>) => (this.records = data));
  }

  updateItem(event: DeviceRecord) {
    this.saveItem(event);
  }

  addItem(event: DeviceRecord) {
    const deviceRecords = this.records.filter(
      (r) => r.device.id == event.device.id
    );
    if (deviceRecords.length != 0) {
      this.openDialog(new DeviceRecordPair(event, deviceRecords[0]));
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: DeviceRecord) {
    this.indexer.index(event).subscribe((deviceRecord) => {
      this.records = this.records.filter(
        (r) => r.device.id != deviceRecord.device.id
      );
      this.records.push(deviceRecord);
    });
  }

  deleteItem(event: DeviceRecord) {
    this.eraser.delete(event).subscribe((device) => {
      this.records = this.records.filter((r) => r.device.id != device.id);
    });
  }

  canEdit() {
    return this.authService.isAllowed(Array.of("device_records:records:edit"))
  }

  canDelete() {
    return this.authService.isAllowed(Array.of("device_records:records:delete"))
  }

}

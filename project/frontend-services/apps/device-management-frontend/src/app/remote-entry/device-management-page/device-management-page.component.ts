import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {DeviceInformation, DeviceRecordPair, DeviceViewType,} from '@frontend-services/device-management/model';
import {
  DeleteDeviceRecord,
  GetAllDeviceRecords,
  IndexDeviceRecord,
} from '@frontend-services/device-management/services';
import {AuthService} from "@frontend-services/simple-auth-lib";
import {DeviceDialogComponent} from "../device-dialog/device-dialog.component";

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './device-management-page.component.html',
  styleUrls: ['./device-management-page.component.scss'],
})
export class DeviceManagementPageComponent implements OnInit {
  records: Array<DeviceInformation> = new Array<DeviceInformation>();

  deviceViewType = DeviceViewType;
  loading = true;

  constructor(
    public dialog: MatDialog,
    private recordsCollector: GetAllDeviceRecords,
    private indexer: IndexDeviceRecord,
    private eraser: DeleteDeviceRecord,
    private authService: AuthService
  ) {
  }

  openDialog(data: DeviceRecordPair) {
    const dialogRef = this.dialog.open(DeviceDialogComponent, {
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
    this.loading = true;
    this.recordsCollector
      .getData()
      .subscribe(
        (data: Array<DeviceInformation>) => (this.records = data.sort((a, b) => a.device.name.localeCompare(b.device.name))),
        error => error,
        () => this.loading = false);
  }

  updateItem(event: DeviceInformation) {
    this.saveItem(event);
  }

  addItem(event: DeviceInformation) {
    const deviceRecords = this.records.filter(
      (r) => r.device.id == event.device.id
    );
    if (deviceRecords.length != 0) {
      this.openDialog(new DeviceRecordPair(event, deviceRecords[0]));
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: DeviceInformation) {
    this.indexer.index(event).subscribe((deviceRecord) => {
      this.records = this.records.filter(
        (r) => r.device.id != deviceRecord.device.id
      );
      this.records.push(deviceRecord);
    });
  }

  deleteItem(event: DeviceInformation) {
    this.eraser.delete(event).subscribe((device) => {
      this.records = this.records.filter((r) => r.device.id != device.id);
    });
  }

  canEdit() {
    return this.authService.isAllowed(Array.of("device_management:device:edit"))
  }

  canDelete() {
    return this.authService.isAllowed(Array.of("device_management:device:delete"))
  }

}

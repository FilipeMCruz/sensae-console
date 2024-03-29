import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {
  DeviceGroup,
  DeviceInformation,
  DeviceInformationPair,
  DeviceViewType,
} from '@frontend-services/device-management/model';
import {
  CommandDeviceService,
  DeleteDeviceInformation,
  GetAllDeviceInformation,
  IndexDeviceInformation,
} from '@frontend-services/device-management/services';
import {DeviceDialogComponent} from "../device-dialog/device-dialog.component";
import {DateFormat} from "@frontend-services/core";

const DAY = 86400;

const HOUR = 3600;

const TEN_MINUTES = 600;

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './device-management-page.component.html',
  styleUrls: ['./device-management-page.component.scss'],
})
export class DeviceManagementPageComponent implements OnInit {
  records: Array<DeviceInformation> = new Array<DeviceInformation>();

  groups = new Array<DeviceGroup>();

  deviceViewType = DeviceViewType;

  loading = true;

  label = "";

  grouped = false;

  constructor(
    public dialog: MatDialog,
    private recordsCollector: GetAllDeviceInformation,
    private indexer: IndexDeviceInformation,
    private eraser: DeleteDeviceInformation,
    private commander: CommandDeviceService,
  ) {
  }

  openDialog(data: DeviceInformationPair) {
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
        (data: Array<DeviceInformation>) => {
          this.groups = DeviceGroup.buildGroups(this.label, data);
          this.records = data.sort((a, b) => a.device.name.localeCompare(b.device.name));
        },
        error => error,
        () => this.loading = false);
  }

  updateItem(event: DeviceInformation) {
    this.saveItem(event);
  }

  addItem(event: DeviceInformation) {
    const deviceRecords = this.records.filter(r => r.device.id == event.device.id);
    if (deviceRecords.length != 0) {
      this.openDialog(new DeviceInformationPair(event, deviceRecords[0]));
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: DeviceInformation) {
    this.indexer.index(event).subscribe((deviceRecord) => {
      this.records = this.records.filter(r => r.device.id != deviceRecord.device.id);
      this.records.push(deviceRecord);
      this.records = this.records.sort((a, b) => a.device.name.localeCompare(b.device.name));
      this.groups.forEach(g => g.tryAdd(deviceRecord));
    });
  }

  deleteItem(event: DeviceInformation) {
    this.eraser.delete(event).subscribe((device) => {
      this.records = this.records.filter((r) => r.device.id != device.id);
      this.groups.forEach(g => g.tryRemove(device));
    });
  }

  canEdit() {
    return this.indexer.canDo();
  }

  canDelete() {
    return this.eraser.canDo();
  }

  groupBy() {
    this.groups = DeviceGroup.buildGroups(this.label, this.groups.flatMap(g => g.devices));
    this.grouped = true;
  }

  clearGroupBy() {
    this.label = "";
    this.groupBy();
    this.grouped = false;
  }

  canCommand() {
    return this.commander.canDo();
  }

  getLastTimeSeen(record: DeviceInformation) {
    const seconds = Math.floor((new Date().valueOf() - record.lastTimeSeen.valueOf()) / 1000);

    const interval = seconds / 31536000;
    if (interval > 1) {
      return "never";
    }

    return DateFormat.timeAgo(record.lastTimeSeen) + " ago";
  }

  getLastTimeSeenColor(record: DeviceInformation) {
    const seconds = Math.floor((new Date().valueOf() - record.lastTimeSeen.valueOf()) / 1000);
    if (seconds >= DAY) return "red-chip";
    else if (seconds >= HOUR) return "orange-chip";
    else if (seconds >= TEN_MINUTES) return "yellow-chip";
    else return "green-chip";
  }
}

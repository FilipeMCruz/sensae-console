import {Component, OnInit} from '@angular/core';
import {GetAllDeviceRecords} from "../../services/GetAllDeviceRecords";
import {DeviceRecord} from "../../model/DeviceRecord";
import {DeviceRecordsQueryMapper} from "../../mappers/DeviceRecordsQueryMapper";
import {IndexDeviceRecord} from "../../services/IndexDeviceRecord";
import {DeviceRecordRegisterMapper} from "../../mappers/DeviceRecordRegisterMapper";
import {DeleteDeviceRecord} from "../../services/DeleteDeviceRecord";
import {DeviceMapper} from "../../mappers/DeviceMapper";
import {MatDialog} from '@angular/material/dialog';
import {DeviceRecordDialogComponent} from "../device-record-dialog/device-record-dialog.component";
import {DeviceRecordPair} from "../../model/DeviceRecordPair";
import {DeviceViewType} from "../../model/DeviceViewType";

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './device-record-page.component.html',
  styleUrls: ['./device-record-page.component.scss']
})
export class DeviceRecordPageComponent implements OnInit {

  records: Array<DeviceRecord> = new Array<DeviceRecord>();

  deviceViewType = DeviceViewType;

  constructor(public dialog: MatDialog,
              private recordsCollector: GetAllDeviceRecords,
              private indexer: IndexDeviceRecord,
              private eraser: DeleteDeviceRecord) {
  }

  openDialog(data: DeviceRecordPair) {
    const dialogRef = this.dialog.open(DeviceRecordDialogComponent, {
      width: '1400px',
      data,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.saveItem(data.fresh);
      }
    });
  }

  ngOnInit(): void {
    this.fetchAllDevices();
  }

  fetchAllDevices() {
    this.recordsCollector.getData().subscribe(({data}) => {
      if (data != null)
        this.records = DeviceRecordsQueryMapper.dtoToModel(data);
    });
  }

  updateItem(event: DeviceRecord) {
    this.saveItem(event);
  }

  addItem(event: DeviceRecord) {
    const deviceRecords = this.records.filter(r => r.device.id == event.device.id);
    if (deviceRecords.length != 0) {
      this.openDialog(new DeviceRecordPair(event, deviceRecords[0]));
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: DeviceRecord) {
    this.indexer.index(DeviceRecordRegisterMapper.modelToDto(event)).subscribe(({data}) => {
      if (data != null) {
        const deviceRecord = DeviceRecordRegisterMapper.dtoToModel(data);
        this.records = this.records.filter(r => r.device.id != deviceRecord.device.id);
        this.records.push(deviceRecord);
      }
    });
  }

  deleteItem(event: DeviceRecord) {
    this.eraser.delete(DeviceMapper.modelToDto(event.device)).subscribe(({data}) => {
      if (data != null) {
        const device = DeviceMapper.dtoToModel(data.delete);
        this.records = this.records.filter(r => r.device.id != device.id);
      }
    });
  }
}

import {Component, OnInit} from '@angular/core';
import {GetAllDeviceRecords} from "../../services/GetAllDeviceRecords";
import {DeviceRecord} from "../../model/DeviceRecord";
import {DeviceRecordsQueryMapper} from "../../mappers/DeviceRecordsQueryMapper";

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './device-record-page.component.html',
  styleUrls: ['./device-record-page.component.scss']
})
export class DeviceRecordPageComponent implements OnInit {

  records: Array<DeviceRecord> = new Array<DeviceRecord>();

  constructor(private recordsCollector: GetAllDeviceRecords) {
  }

  ngOnInit(): void {
    this.recordsCollector.getData().subscribe(({data}) => {
      this.records = DeviceRecordsQueryMapper.dtoToModel(data);
    });
  }
}

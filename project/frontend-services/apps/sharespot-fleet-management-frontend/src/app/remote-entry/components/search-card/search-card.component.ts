import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Device } from '../../model/Device';
import { FormControl, FormGroup } from '@angular/forms';
import { DeviceHistoryQuery } from '../../model/pastdata/DeviceHistoryQuery';

@Component({
  selector: 'frontend-services-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss'],
})
export class SearchCardComponent {
  @Output() devicesPicked = new EventEmitter<Array<Device>>();
  @Output() contentPicked = new EventEmitter<string>();
  @Output() deviceCleaned = new EventEmitter<null>();
  @Output() contentCleaned = new EventEmitter<null>();
  @Output() deviceHistory = new EventEmitter<DeviceHistoryQuery>();
  @Output() historyCleaned = new EventEmitter<null>();
  @Input() devices: Array<Device> = [];

  selectedDevices = new FormControl([]);
  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  searchContent = '';
  queryType = 'devices';
  dateQueryType = 'liveData';
  allSelected = false;
  panelOpenState = true;

  onContentClick() {
    if (this.searchContent.trim().length > 0) {
      this.pickContent(this.searchContent);
    }
  }

  onContentClear() {
    this.searchContent = '';
    this.cleanContent();
  }

  onDeviceClick() {
    if (this.selectedDevices) {
      const devices = this.selectedDevices.value as Array<Device>;
      if (devices.length > 0) {
        this.pickDevices(devices);
      }
    }
  }

  onDeviceClear() {
    this.cleanDevice();
  }

  onDeviceHistoryClear() {
    this.cleanHistory();
  }

  onHistoryClick() {
    if (this.selectedDevices) {
      const devices = this.selectedDevices.value as Array<Device>;
      if (devices.length > 0) {
        const result = new Date(this.range.value.end);
        result.setDate(result.getDate() + 1);
        this.pickHistory(
          new DeviceHistoryQuery(devices, this.range.value.start, result)
        );
      }
    }
  }

  public pickContent(id: string): void {
    this.contentPicked.emit(id);
  }

  public cleanContent(): void {
    this.contentCleaned.emit();
  }

  public pickDevices(devices: Array<Device>): void {
    this.devicesPicked.emit(devices);
  }

  public cleanDevice(): void {
    this.deviceCleaned.emit();
  }

  public pickHistory(query: DeviceHistoryQuery): void {
    this.deviceHistory.emit(query);
  }

  public cleanHistory(): void {
    this.historyCleaned.emit();
  }

  validHistoryQuery() {
    if (this.selectedDevices) {
      const devices = this.selectedDevices.value as Array<Device>;
      if (devices && devices.length > 0) {
        return this.range.valid;
      }
    }
    return false;
  }

  clearDevices() {
    this.selectedDevices.setValue([]);
  }

  selectAllDevices() {
    this.selectedDevices.setValue(this.devices);
  }

  toggleAllSelection() {
    if (this.allSelected) {
      this.selectedDevices.setValue(this.devices);
    } else {
      this.selectedDevices.setValue([]);
    }
  }

  valid() {
    if (
      this.queryType === 'devices' &&
      this.selectedDevices.value.length === 0
    ) {
      return false;
    }
    if (
      this.queryType === 'content' &&
      this.searchContent.trim().length === 0
    ) {
      return false;
    }
    if (
      this.dateQueryType === 'pastData' &&
      (!this.range.value.start ||
        !this.range.value.end ||
        this.range.value.start > this.range.value.end)
    ) {
      return false;
    }
    if (this.dateQueryType === 'pastData' && this.queryType === 'content') {
      return false; //Currently not supported
    }
    return true;
  }

  applyFilter() {
    if (this.dateQueryType === 'pastData' && this.queryType === 'devices') {
      this.onHistoryClick();
      return;
    }
    if (this.dateQueryType === 'liveData' && this.queryType === 'devices') {
      this.onDeviceClick();
      return;
    }
    if (this.dateQueryType === 'liveData' && this.queryType === 'content') {
      this.onContentClick();
      return;
    }
  }
}

import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Device} from "../../model/Device";
import {FormControl, FormGroup} from "@angular/forms";
import {DeviceHistoryQuery} from "../../model/DeviceHistoryQuery";

@Component({
  selector: 'frontend-services-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss']
})
export class SearchCardComponent {

  @Output() devicesPicked = new EventEmitter<Array<Device>>();
  @Output() contentPicked = new EventEmitter<string>();
  @Output() deviceCleaned = new EventEmitter<null>();
  @Output() contentCleaned = new EventEmitter<null>();
  @Output() deviceHistory = new EventEmitter<DeviceHistoryQuery>();
  @Output() historyCleaned = new EventEmitter<null>();
  @Input() devices: Array<Device> = [];

  selectedDevices = new FormControl();
  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  searchContent = "";

  onContentClick() {
    if (this.searchContent.trim().length > 0) {
      this.pickContent(this.searchContent);
    }
  }

  onContentClear() {
    this.searchContent = "";
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
        this.pickHistory(new DeviceHistoryQuery(devices, this.range.value.start, this.range.value.end));
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
    return false
  }
}

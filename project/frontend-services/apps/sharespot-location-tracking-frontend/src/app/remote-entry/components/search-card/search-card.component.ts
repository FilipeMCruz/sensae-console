import {Component, EventEmitter, Output} from '@angular/core';
import {GPSSensorDataQuery} from "../../dtos/SensorDTO";

@Component({
  selector: 'frontend-services-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss']
})
export class SearchCardComponent {

  @Output() devicePicked = new EventEmitter<string>();
  @Output() contentPicked = new EventEmitter<string>();
  @Output() deviceCleaned = new EventEmitter<null>();
  @Output() contentCleaned = new EventEmitter<null>();
  @Output() deviceHistory = new EventEmitter<GPSSensorDataQuery>();
  @Output() historyCleaned = new EventEmitter<null>();

  searchDevice = "";
  searchContent = "";
  searchCurrent = "None";
  searchCurrentType = "";

  historyDevice = "";
  historyStartDate!: Date;
  historyEndDate!: Date;
  historyCurrent = "None";

  onContentClick() {
    if (this.searchContent.trim().length > 0) {
      this.pickContent(this.searchContent);
      this.searchCurrentType = " Content";
      this.searchCurrent = this.searchContent + this.searchCurrentType;
    }
  }

  onContentClear() {
    if (this.searchCurrentType == " Content") this.searchCurrent = "None";
    this.searchContent = "";
    this.cleanContent();
  }

  onDeviceClick() {
    if (this.searchDevice.trim().length > 0) {
      this.pickDevice(this.searchDevice);
      this.searchCurrentType = " Device";
      this.searchCurrent = this.searchDevice + this.searchCurrentType;
    }
  }

  onDeviceClear() {
    if (this.searchCurrentType == " Device") this.searchCurrent = "None";
    this.searchDevice = "";
    this.cleanDevice();
  }

  onDeviceHistoryClear() {
    this.historyCurrent = "None";
    this.historyDevice = "";
    this.cleanHistory();
  }

  onHistoryClick() {
    this.historyCurrent = this.historyDevice + " Device";
    const query: GPSSensorDataQuery = {
      device: this.historyDevice,
      endTime: Math.round(this.historyEndDate.getTime() / 1000).toString(),
      startTime: Math.round(this.historyStartDate.getTime() / 1000).toString()
    }
    this.pickHistory(query);
  }

  public pickContent(id: string): void {
    this.contentPicked.emit(id);
  }

  public cleanContent(): void {
    this.contentCleaned.emit();
  }

  public pickDevice(id: string): void {
    this.devicePicked.emit(id);
  }

  public cleanDevice(): void {
    this.deviceCleaned.emit();
  }

  public pickHistory(query: GPSSensorDataQuery): void {
    this.deviceHistory.emit(query);
  }

  public cleanHistory(): void {
    this.historyCleaned.emit();
  }

  validHistoryQuery() {
    return this.historyDevice.trim().length !== 0 &&
      (this.historyStartDate == undefined ||
        (this.historyEndDate != undefined && this.historyEndDate >= this.historyStartDate))
  }
}

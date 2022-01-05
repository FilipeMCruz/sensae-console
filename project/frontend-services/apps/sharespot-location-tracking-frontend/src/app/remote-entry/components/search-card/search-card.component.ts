import {Component, EventEmitter, Output} from '@angular/core';

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

  device = "";
  content = "";
  current = "None";
  currentType = "";

  onContentClick() {
    if (this.content.trim().length > 0) {
      this.pickContent(this.content);
      this.currentType = " Content";
      this.current = this.content + this.currentType;
    }
  }

  onContentClear() {
    if (this.currentType == " Content") this.current = "None";
    this.content = "";
    this.cleanContent();
  }

  onDeviceClick() {
    if (this.device.trim().length > 0) {
      this.pickDevice(this.device);
      this.currentType = " Device";
      this.current = this.device + this.currentType;
    }
  }

  onDeviceClear() {
    if (this.currentType == " Device") this.current = "None";
    this.device = "";
    this.cleanDevice();
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
}

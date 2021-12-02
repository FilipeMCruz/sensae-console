import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'frontend-services-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.scss']
})
export class SearchCardComponent {

  @Output() devicePicked = new EventEmitter<string>();
  @Output() deviceCleaned = new EventEmitter<null>();

  value = "";

  onClick() {
    if (this.value.trim().length > 0)
      this.pickDevice(this.value);
  }

  onClear() {
    this.value = "";
    this.cleanDevice();
  }

  public pickDevice(id: string): void {
    this.devicePicked.emit(id);
  }

  public cleanDevice(): void {
    this.deviceCleaned.emit();
  }
}

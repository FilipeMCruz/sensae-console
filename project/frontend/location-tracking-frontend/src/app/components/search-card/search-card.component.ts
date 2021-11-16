import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.css']
})
export class SearchCardComponent implements OnInit {

  @Output() onDevicePicked = new EventEmitter<string>();
  @Output() onDeviceClean = new EventEmitter<null>();

  value: string = "";

  constructor() {
  }

  ngOnInit(): void {
  }

  onClear() {
    this.value = "";
    this.cleanDevice();
  }

  onClick() {
    this.pickDevice(this.value);
  }

  public pickDevice(id: string): void {
    this.onDevicePicked.emit(id);
  }

  public cleanDevice(): void {
    this.onDeviceClean.emit();
  }
}

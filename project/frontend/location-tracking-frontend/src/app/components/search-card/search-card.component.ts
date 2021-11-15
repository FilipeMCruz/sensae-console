import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-search-card',
  templateUrl: './search-card.component.html',
  styleUrls: ['./search-card.component.css']
})
export class SearchCardComponent implements OnInit {

  @Output() onDevicePicked = new EventEmitter<string>();

  value: string = "";

  constructor() {
  }

  ngOnInit(): void {
  }

  onClick() {
    this.pickDevice(this.value);
  }

  public pickDevice(id: string): void {
    this.onDevicePicked.emit(id);
  }
}

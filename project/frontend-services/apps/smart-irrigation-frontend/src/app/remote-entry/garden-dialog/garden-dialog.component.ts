import {AfterViewInit, Component, Inject, OnDestroy} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {
  Data,
  DataFilters,
  DeviceType,
  GardeningArea,
  LatestDataQueryFilters
} from "@frontend-services/smart-irrigation/model";
import {Subscription} from "rxjs";
import {FetchLatestData, SubscribeToData} from "@frontend-services/smart-irrigation/services";

@Component({
  selector: 'frontend-services-garden-dialog',
  templateUrl: 'garden-dialog.component.html',
  styleUrls: ['./garden-dialog.component.scss'],
})
export class GardenDialogComponent implements AfterViewInit, OnDestroy {

  loadingInfo = false;

  valvesData: Data[] = [];

  sensorsData: Data[] = [];

  private subscription!: Subscription;

  constructor(private fetchLatestDataService: FetchLatestData,
              private subscribeToDataService: SubscribeToData,
              public dialogRef: MatDialogRef<GardenDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: GardeningArea
  ) {
  }

  private static onNewData(data: Data[], newData: Data) {
    const found = data.find(d => d.device.id);
    if (found) {
      found.update(newData);
    } else {
      data.push(newData);
    }
  }

  ngAfterViewInit(): void {
    this.fetchLatestData();
    this.subscribeToData();
  }

  ngOnDestroy() {
    if (this.subscription)
      this.subscription.unsubscribe();
  }

  onSelect(sensorData: Data) {
    console.log(sensorData)
  }

  private fetchLatestData() {
    const filter = new LatestDataQueryFilters([], [this.data.id]);
    this.fetchLatestDataService.getData(filter).subscribe(
      next => {
        this.valvesData.push(...next.filter(d => d.device.type === DeviceType.VALVE));
        this.sensorsData.push(...next.filter(d => d.device.type !== DeviceType.VALVE));
      },
      error => error,
      () => this.loadingInfo = false);
  }

  private subscribeToData() {
    const filter = new DataFilters([], [this.data.id], "");
    this.subscription = this.subscribeToDataService.getData(filter).subscribe(next => {
        if (next.device.type.valueOf() === DeviceType.VALVE.valueOf()) {
          GardenDialogComponent.onNewData(this.valvesData, next);
        } else {
          GardenDialogComponent.onNewData(this.sensorsData, next);
        }
      },
      error => error);
  }
}

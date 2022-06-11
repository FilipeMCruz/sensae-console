import {AfterViewInit, Component, Inject, OnDestroy} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {
  Data,
  DataFilters,
  DeviceType,
  IrrigationZone,
  LatestDataQueryFilters
} from "@frontend-services/smart-irrigation/model";
import {Subscription} from "rxjs";
import {
  FetchHistory,
  FetchLatestData,
  SubscribeToData,
  SwitchValve
} from "@frontend-services/smart-irrigation/services";
import {ValveDialogComponent} from "../valve-dialog/valve-dialog.component";
import * as mapboxgl from "mapbox-gl";
import {environment} from "../../../environments/environment";
import {GeoJSONSource, LngLatBoundsLike, LngLatLike} from "mapbox-gl";
import {DeviceHistoryDialogComponent} from "../device-history-dialog/device-history-dialog.component";

@Component({
  selector: 'frontend-services-garden-dialog',
  templateUrl: 'zone-dialog.component.html',
  styleUrls: ['./zone-dialog.component.scss'],
})
export class ZoneDialogComponent implements AfterViewInit, OnDestroy {

  loadingInfo = false;

  valvesData: Data[] = [];

  sensorsData: Data[] = [];

  private subscription!: Subscription;

  private map!: mapboxgl.Map;

  constructor(private fetchLatestDataService: FetchLatestData,
              private fetchHistoryService: FetchHistory,
              private subscribeToDataService: SubscribeToData,
              private switchValveService: SwitchValve,
              public dialogRef: MatDialogRef<ZoneDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: IrrigationZone,
              public dialog: MatDialog
  ) {
  }

  private static onNewData(data: Data[], newData: Data) {
    const found = data.find(d => d.device.id.value === newData.device.id.value);
    if (found) {
      found.update(newData);
    } else {
      data.push(newData);
    }
  }

  ngAfterViewInit(): void {
    const loader = document.getElementById('loader');
    if (loader) loader.className = '';

    this.buildMap();
    this.fetchLatestData();
    this.subscribeToData();
  }

  ngOnDestroy() {
    if (this.subscription)
      this.subscription.unsubscribe();
  }

  buildMap(): void {
    this.map = new mapboxgl.Map({
      container: 'map-details',
      style: 'mapbox://styles/mapbox/satellite-v9',
      center: this.data.center() as LngLatLike,
      bounds: this.data.bounds() as LngLatBoundsLike,
      interactive: false
    });
    this.map.addControl(new mapboxgl.NavigationControl());
    this.map.on('load', () => {

      const loader = document.getElementById('loader');
      if (loader) loader.className = 'done';

      setTimeout(() => {
        if (loader) loader.className = 'hide';
        this.map.resize();
        this.map.fitBounds(this.data.bounds() as LngLatBoundsLike, {padding: 100});
      }, 500)
    });
  }

  canInteractWithValve(sensorData: Data) {
    return sensorData.device.remoteControl && !sensorData.device.switchQueued && this.switchValveService.canDo();
  }

  onSelect(sensorData: Data) {
    if (this.canInteractWithValve(sensorData)) {
      const dialogRef = this.dialog.open(ValveDialogComponent, {
        width: '350px',
        data: sensorData,
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) this.switchValveService.execute(sensorData.device).subscribe();
      });
    }
  }

  private fetchLatestData() {
    const filter = new LatestDataQueryFilters([], [this.data.id]);
    this.fetchLatestDataService.getData(filter).subscribe(
      next => {
        this.valvesData.push(...next.filter(d => d.device.type === DeviceType.VALVE));
        this.sensorsData.push(...next.filter(d => d.device.type !== DeviceType.VALVE));
        this.drawDevices();
        this.updateDeviceSource();
      },
      error => error,
      () => this.loadingInfo = false);
  }

  drawDevices() {
    this.map.on('load', () => {
      this.map.addSource("devices", {
        'type': 'geojson',
        data: {
          'type': 'FeatureCollection',
          'features': []
        }
      });
      this.map.addLayer(Data.getDataStyle("devices"));
      this.map.addLayer(Data.getHoverDataStyle("devices"));
      this.map.addLayer(Data.getIlluminanceDataStyle("devices"))
      this.map.addLayer(Data.getTemperatureDataStyle("devices"))
      this.map.addLayer(Data.getHumidityDataStyle("devices"))
      this.map.addLayer(Data.getSoilMoistureDataStyle("devices"))
      this.map.setFilter("hoverDevices", [
        'match',
        ['get', 'id'],
        'none',
        true,
        false
      ])
    });
  }

  openDeviceHistory(sensorData: Data) {
    this.dialog.open(DeviceHistoryDialogComponent, {
      width: '70%',
      height: '80%',
      data: sensorData
    });
  }

  private subscribeToData() {
    const filter = new DataFilters([], [this.data.id], "");
    this.subscription = this.subscribeToDataService.getData(filter).subscribe(next => {
        if (next.device.type.valueOf() === DeviceType.VALVE.valueOf()) {
          ZoneDialogComponent.onNewData(this.valvesData, next);
        } else {
          ZoneDialogComponent.onNewData(this.sensorsData, next);
        }
        this.updateDeviceSource();
      },
      error => error);
  }

  private updateDeviceSource() {
    const features = [...this.sensorsData];
    features.push(...this.valvesData);
    this.map.on('load', () => {
      (this.map.getSource("devices") as GeoJSONSource).setData({
        'type': 'FeatureCollection',
        'features': features.map(g => g.asFeature())
      })
    });
  }

  onHoverEnter(sensorData: Data) {
    this.map.setFilter("hoverDevices", [
      'match',
      ['get', 'id'],
      sensorData.device.id.value,
      true,
      false
    ])
  }

  onHoverLeave(sensorData: Data) {
    this.map.setFilter("hoverDevices", [
      'match',
      ['get', 'id'],
      'none',
      true,
      false
    ])
  }
}

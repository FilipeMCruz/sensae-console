import {Component, OnDestroy, OnInit} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {GPSSensorData} from "../../model/GPSSensorData";
import {GPSPointData} from "../../model/GPSPointData";
import {Subscription} from "rxjs";
import {SubscribeToGPDDataByDevice} from "../../services/SubscribeToGPDDataByDevice";
import {SensorMapper} from "../../mappers/SensorMapper";
import {SubscribeToAllGPSData} from "../../services/SubscribeToAllGPSData";
import {environment} from "../../../../environments/environment";
import {GPSSensorDataQuery, SensorDTO} from "../../dtos/SensorDTO";
import {SubscribeToGPSDataByContent} from "../../services/SubscribeToGPSDataByContent";
import {QueryGPSDeviceHistory} from "../../services/QueryGPSDeviceHistory";
import {DeviceHistory} from "../../model/DeviceHistory";

@Component({
  selector: 'frontend-services-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit, OnDestroy {

  public follow = false;

  private map!: mapboxgl.Map;

  private lat = 40.6045295;

  private lng = -7.8474056;

  private points: Array<GPSPointData> = new Array<GPSPointData>();

  private history!: DeviceHistory;

  private followContent = "";

  private subscription!: Subscription;

  constructor(private locationEmitter: SubscribeToGPDDataByDevice,
              private locationByDeviceIdEmitter: SubscribeToAllGPSData,
              private locationByContentEmitter: SubscribeToGPSDataByContent,
              private historyQuery: QueryGPSDeviceHistory) {
  }

  ngOnInit(): void {
    this.initializeMap();
    this.subscription = this.locationEmitter.getData().subscribe(
      next => this.verifyAndDraw(next.data)
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  buttonInfo(): string {
    return (this.follow ? "Press to stop following " : "Press to follow ") + this.followContent;
  }

  buildHistory(filters: GPSSensorDataQuery) {
    this.historyQuery.getData(filters).subscribe(
      next => {
        if (next.data != undefined) {
          if (this.history != undefined) {
            this.map.removeLayer('route');
            this.map.removeSource('route');
          }
          this.history = SensorMapper.dtoToModelHistory(next.data);
          this.map.addSource('route', this.history.asGeoJSON());
          this.map.addLayer(DeviceHistory.buildLayer('route'));
        }
      }
    )
  }

  changeFollow() {
    this.follow = !this.follow;
  }

  cleanSubscriber() {
    this.subscription.unsubscribe();
    this.subscription = this.locationEmitter.getData().subscribe(
      next => this.verifyAndDraw(next.data)
    );
  }

  subscribeToDevice(deviceId: string) {
    this.subscription.unsubscribe();
    this.followContent = deviceId;
    this.points.forEach((sensor, index, array) => {
      if (!sensor.value.device.has(deviceId)) {
        sensor.point.remove();
        array.splice(index, 1);
      }
    });

    this.subscription = this.locationByDeviceIdEmitter.getData(deviceId).subscribe(
      next => {
        this.verifyAndDraw(next.data)
        this.jumpToFirstPoint();
      }
    );
    this.jumpToFirstPoint();
  }

  subscribeToContent(content: string) {
    this.subscription.unsubscribe();
    this.followContent = "";
    this.follow = false;
    this.points.forEach((sensor, index, array) => {
      if (!sensor.value.device.hasContent(content)) {
        sensor.point.remove();
        array.splice(index, 1);
      }
    });

    this.subscription = this.locationByContentEmitter.getData(content).subscribe(
      next => {
        this.verifyAndDraw(next.data)
      }
    );
  }

  cleanHistory() {
    this.map.removeSource('route');
  }

  private verifyAndDraw(data: SensorDTO | null | undefined) {
    if (data !== undefined && data !== null) {
      this.drawPoint(SensorMapper.dtoToModel(data));
    }
  }

  private initializeMap(): void {
    /// locate the user
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        this.map.flyTo({
          center: [this.lng, this.lat]
        });
      });
    }
    this.buildMap();
  }

  buildMap(): void {
    this.map = new mapboxgl.Map({
      container: 'map',
      style: environment.mapbox.style,
      zoom: 8,
      center: [this.lng, this.lat]
    });
    this.map.addControl(new mapboxgl.NavigationControl());
    this.map.on('load', () => this.map.resize());
  }

  private drawPoint(sensor: GPSSensorData): void {
    const found = this.points.find(point => point.isSameSensor(sensor));
    if (found === undefined) {
      const newPoint = new GPSPointData(sensor);
      newPoint.point.addTo(this.map)
      this.points.push(newPoint)
    } else {
      found.updateGPSData(sensor)
    }
  }

  private jumpToFirstPoint() {
    if (this.follow && this.points.length != 0) {
      this.map.flyTo({
        center: this.points[0].point.getLngLat(),
        zoom: 12
      })
    }
  }
}

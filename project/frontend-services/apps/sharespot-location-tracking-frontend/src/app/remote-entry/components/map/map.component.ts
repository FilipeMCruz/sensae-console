import {Component, OnDestroy, OnInit} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {DeviceData} from "../../model/DeviceData";
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
import {QueryLatestGPSDeviceData} from "../../services/QueryLatestGPSDeviceData";

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

  private history?: DeviceHistory;

  private followContent = "";

  private subscription!: Subscription;

  constructor(private locationEmitter: SubscribeToGPDDataByDevice,
              private locationByDeviceIdEmitter: SubscribeToAllGPSData,
              private locationByContentEmitter: SubscribeToGPSDataByContent,
              private historyQuery: QueryGPSDeviceHistory,
              private latestDeviceData: QueryLatestGPSDeviceData) {
  }

  ngOnInit(): void {
    this.initializeMap();
    this.latestDeviceData.getData().subscribe(
      next => {
        if (next.data && next.data.latest) next.data.latest.forEach(d => this.verifyAndDraw(d, "#a9d6e5"))
      }
    )
    ;
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
          this.cleanHistory();
          this.history = SensorMapper.dtoToModelHistory(next.data);
          this.addHistory();
          this.calculateDistance();
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

  addHistory() {
    if (this.history == undefined) return;
    this.map.addSource('route', this.history.asGeoJSON());
    this.map.addLayer(DeviceHistory.buildLayer('route'));
  }

  cleanHistory() {
    if (this.history == undefined) return;
    this.map.removeLayer('route');
    this.map.removeSource('route');
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

  private calculateDistance() {
    const popup = new mapboxgl.Popup({maxWidth: 'none'});
    if (this.history == undefined) return;
    const distance = this.history.distance;
    this.map.on('click', 'route', (e) => {
      popup.setLngLat(e.lngLat)
        .setHTML("<strong>Distance Travelled:</strong> " + distance + " kilometers.")
        .addTo(this.map);
    });
    this.map.on('mouseenter', 'route', () => {
      this.map.getCanvas().style.cursor = 'pointer';
    });
    this.map.on('mouseleave', 'route', () => {
      this.map.getCanvas().style.cursor = '';
    });
  }

  private verifyAndDraw(data: SensorDTO | null | undefined, color?: string) {
    if (data !== undefined && data !== null) {
      this.drawPoint(SensorMapper.dtoToModel(data), color);
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

  private drawPoint(sensor: DeviceData, color?: string): void {
    //TODO: remove once we have a way to deal with errors
    if (sensor.data.gps.longitude < 2 && sensor.data.gps.longitude > -2 && sensor.data.gps.latitude < 2 && sensor.data.gps.latitude > -2) {
      return;
    }

    const found = this.points.find(point => point.isSameSensor(sensor));
    if (found === undefined) {
      const newPoint = new GPSPointData(sensor, color);
      newPoint.point.addTo(this.map);
      this.points.push(newPoint);
    } else {
      if (found.willChangeColor(sensor)) {
        this.points.forEach((sensor, index, array) => {
          if (found.isSameSensor(sensor.value)) {
            sensor.point.remove();
            array.splice(index, 1);
          }
        });
        const newPoint = new GPSPointData(sensor, color);
        newPoint.point.addTo(this.map);
        this.points.push(newPoint);
      } else {
        found.updateGPSData(sensor)
      }
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

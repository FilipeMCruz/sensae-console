import {Component, OnDestroy, OnInit} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {GPSSensorData} from "../../model/GPSSensorData";
import {GPSPointData} from "../../model/GPSPointData";
import {Subscription} from "rxjs";
import {GetNewGPSLocations} from "../../services/GetNewGPSLocations";
import {SensorMapper} from "../../mappers/SensorMapper";
import {GetNewGPSLocation} from "../../services/GetNewGPSLocation";
import {SensorDTO} from "../../dtos/SensorDTO";
import {environment} from "../../../../environments/environment";

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

  private followContent = "";

  private subscription!: Subscription;

  constructor(private locationsEmitter: GetNewGPSLocations,
              private locationEmitter: GetNewGPSLocation) {
  }

  ngOnInit(): void {
    this.initializeMap();
    this.subscription = this.locationsEmitter.getData().subscribe(
      next => this.verifyAndDraw(next.data)
    );
    // const a = new GPSSensorData("841e28de-be0f-491e-a175-816613dfabc6",
    //   new Sensor(`ec53bf69-acbb-4f4a-95e3-5c46d58009c3`, "Oi"),
    //   new Date(1637068401 * 1000),
    //   new SensorCoordinates(41.178940, -8.582296), [])
    // this.drawPoint(a);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  buttonInfo(): string {
    if (this.follow) {
      return "Press to stop following " + this.followContent;
    }
    return "Press to follow " + this.followContent;
  }

  changeFollow() {
    this.follow = !this.follow;
  }

  cleanSubscriber() {
    this.subscription.unsubscribe();
    this.subscription = this.locationsEmitter.getData().subscribe(
      next => this.verifyAndDraw(next.data)
    );
  }

  subscribeTo(deviceId: string) {
    this.subscription.unsubscribe();
    this.followContent = deviceId;
    this.points.forEach((sensor, index, array) => {
      if (sensor.value.device.has(deviceId)) {
        sensor.point.remove();
        array.splice(index, 1);
      }
    });

    this.subscription = this.locationEmitter.getData(deviceId).subscribe(
      next => {
        this.verifyAndDraw(next.data)
        this.jumpToFirstPoint();
      }
    );
    this.jumpToFirstPoint();
  }

  private verifyAndDraw(data: SensorDTO | null | undefined) {
    if (data !== undefined && data !== null) {
      console.log(data)
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

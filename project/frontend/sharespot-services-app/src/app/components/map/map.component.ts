import {Component, OnDestroy, OnInit} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {GPSSensorData} from "../../model/GPSSensorData";
import {GPSPointData} from "../../model/GPSPointData";
import {Subscription} from "rxjs";
import {GetNewGPSLocations} from "../../services/GetNewGPSLocations";
import {SensorMapper} from "../../mappers/SensorMapper";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit, OnDestroy {

  private map!: mapboxgl.Map;

  private lat = 40.6045295;

  private lng = -7.8474056;

  private points: Array<GPSPointData> = new Array<GPSPointData>();

  private subscription!: Subscription;

  constructor(private service: GetNewGPSLocations) {
  }

  ngOnInit(): void {
    this.initializeMap();
    this.subscription = this.service.getData().subscribe(
      next => {
        if (next.data !== undefined && next.data !== null)
          this.drawPoint(SensorMapper.dtoToModel(next.data));
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
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
      zoom: 10,
      center: [this.lng, this.lat]
    });
    this.map.addControl(new mapboxgl.NavigationControl());
  }

  drawPoint(sensor: GPSSensorData): void {
    let found = this.points.find(point => point.isSameSensor(sensor));
    if (found === undefined) {
      let newPoint = new GPSPointData(sensor);
      newPoint.point.addTo(this.map)
      this.points.push(newPoint)
    } else {
      found.updateGPSData(sensor)
    }
  }
}

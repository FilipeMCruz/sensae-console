import {AfterViewInit, Component, ElementRef, OnDestroy, ViewChild} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {Subscription} from 'rxjs';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'frontend-services-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements AfterViewInit, OnDestroy {

  private map!: mapboxgl.Map;

  private lat = 40.6045295;

  private lng = -7.8474056;

  private subscription!: Subscription;

  constructor() {
  }

  ngAfterViewInit(): void {
    this.initializeMap();
  }

  ngOnDestroy() {
    // this.subscription.unsubscribe();
  }

  buildMap(): void {
    this.map = new mapboxgl.Map({
      container: 'map',
      style: environment.mapbox.style,
      zoom: 8,
      center: [this.lng, this.lat],
    });
    this.map.addControl(new mapboxgl.NavigationControl());
    this.map.on('load', () => this.map.resize());
  }

  private initializeMap(): void {
    /// locate the user
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        this.map.flyTo({
          center: [this.lng, this.lat],
        });
      });
    }
    this.buildMap();
  }
}

import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {Subscription} from 'rxjs';
import {environment} from "../../../environments/environment";
import {CreateGardeningAreaCommand, GardeningArea} from "@frontend-services/smart-irrigation/model";
import {CreateGarden, FetchGardens} from "@frontend-services/smart-irrigation/services";
import * as MapboxDraw from "@mapbox/mapbox-gl-draw";
import {Polygon} from "geojson";
import {LngLatLike} from "mapbox-gl";

@Component({
  selector: 'frontend-services-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements AfterViewInit, OnDestroy {

  private map!: mapboxgl.Map;
  private draw!: MapboxDraw;
  private lat = 40.6045295;

  private lng = -7.8474056;

  private subscription!: Subscription;

  public gardens: Array<GardeningArea> = [];

  public loadingGardens = true;

  public isDrawing = false;

  public gardenName = "";

  constructor(private fetchGardensService: FetchGardens, private createGardenService: CreateGarden) {
  }

  ngAfterViewInit(): void {
    this.initializeMap();
    this.fetchGardens();
  }

  private fetchGardens() {
    this.loadingGardens = true;
    this.fetchGardensService.getData().subscribe(
      next => this.drawGardens(next),
      error => error,
      () => this.loadingGardens = false
    );
  }

  private drawGardens(next: Array<GardeningArea>) {
    this.gardens = next;
    this.map.on('load', () => {
      this.map.addSource("gardens", {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': this.gardens.map(g => g.asFeature())
        }
      })
      this.map.addLayer(GardeningArea.getBoundaryStyle());
      this.map.addLayer(GardeningArea.getLabelStyle());
      this.map.addLayer(GardeningArea.getAreaStyle());
    });

    this.loadingGardens = false;
  }

  ngOnDestroy() {
    if (this.subscription)
      this.subscription.unsubscribe();
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
    if (navigator.geolocation
    ) {
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

  startDrawGarden() {
    this.draw = new MapboxDraw({
      displayControlsDefault: false,
      defaultMode: 'draw_polygon'
    });
    this.map.addControl(this.draw);
    this.isDrawing = true;
  }

  deleteGarden() {
    const data = this.draw.getAll();
    if (data.features.length > 0) {
      const id = data.features[0].id;
      if (id)
        this.draw.delete(id.toLocaleString());
    }
    this.map.removeControl(this.draw);
    this.isDrawing = false;
  }

  buildGarden() {
    const gardenArea = this.draw.getSelected().features[0].geometry as Polygon;
    const command = CreateGardeningAreaCommand.build(this.gardenName, gardenArea.coordinates[0]);
    this.createGardenService.execute(command).subscribe(
      next => this.gardens.push(next),
      error => error,
      () => this.isDrawing = false);
  }

  onSelect(garden: GardeningArea) {
    this.map.flyTo({
      center: garden.center() as LngLatLike,
      zoom: 20,
      essential: true // this animation is considered essential with respect to prefers-reduced-motion
    })
  }
}

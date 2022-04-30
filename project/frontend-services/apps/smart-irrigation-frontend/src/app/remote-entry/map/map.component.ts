import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {Subscription} from 'rxjs';
import {environment} from "../../../environments/environment";
import {
  CreateGardeningAreaCommand, Data, DeleteGardeningAreaCommand,
  GardeningArea,
  LatestDataQueryFilters
} from "@frontend-services/smart-irrigation/model";
import {CreateGarden, DeleteGarden, FetchGardens, FetchLatestData} from "@frontend-services/smart-irrigation/services";
import * as MapboxDraw from "@mapbox/mapbox-gl-draw";
import {Polygon} from "geojson";
import {EventData, GeoJSONSource, LngLatBounds, LngLatBoundsLike, LngLatLike, MapLayerEventType} from "mapbox-gl";
import {MatDialog} from "@angular/material/dialog";
import {GardenDialogComponent} from "../garden-dialog/garden-dialog.component";

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

  public latestData: Data[] = [];

  public gardens: Array<GardeningArea> = [];

  public loadingGardens = true;

  public isDrawing = false;

  public isSketchValid = false;

  public gardenName = "";

  constructor(private fetchGardensService: FetchGardens,
              private createGardenService: CreateGarden,
              private deleteGardenService: DeleteGarden,
              private fetchLatestDataService: FetchLatestData,
              public dialog: MatDialog) {
  }

  ngAfterViewInit(): void {
    this.initializeMap();
    this.fetchGardens();
    this.fetchLatestData();
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
      this.map.addLayer(GardeningArea.getBoundaryStyle("gardens"));
      this.map.addLayer(GardeningArea.getLabelStyle("gardens"));
      this.map.addLayer(GardeningArea.getAreaStyle("gardens"));
      this.map.on('click', 'gardens', (e) => {
        if (e.features && e.features[0] && e.features[0].properties && e.features[0].properties["id"])
          this.openGardenDetails(e.features[0].properties["id"]);
      });
      this.map.on('mouseenter', 'gardens', () => {
        this.map.getCanvas().style.cursor = 'pointer';
      });

      this.map.on('mouseleave', 'gardens', () => {
        this.map.getCanvas().style.cursor = '';
      });
    });
    this.loadingGardens = false;
  }

  private updateGardensSource() {
    console.log("Oi mate")
    const source = this.map.getSource("gardens") as GeoJSONSource;
    source.setData({
      'type': 'FeatureCollection',
      'features': this.gardens.map(g => g.asFeature())
    });
  }

  openGardenDetails(id: string) {
    this.dialog.open(GardenDialogComponent, {
      width: '70%',
      height: '80%',
      data: this.gardens.find(g => g.id.value == id),
      disableClose: true,
    });
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

  startGardenSketch() {
    this.draw = new MapboxDraw({
      displayControlsDefault: false,
      defaultMode: 'draw_polygon'
    });
    this.map.addControl(this.draw);
    this.map.on('draw.create', () => this.checkIfSketchIsValid());
    this.map.on('draw.update', () => this.checkIfSketchIsValid());
    this.isDrawing = true;
  }

  deleteGardenSketch() {
    const data = this.draw.getAll();
    if (data.features.length > 0) {
      const id = data.features[0].id;
      if (id) this.draw.delete(id.toLocaleString());
    }
    this.map.removeControl(this.draw);
    this.isDrawing = false;
    this.gardenName = "";
  }

  fetchLatestData() {
    const filter = new LatestDataQueryFilters([], []);
    this.fetchLatestDataService.getData(filter).subscribe(
      next => this.drawLatestData(next),
      error => error);
  }

  drawLatestData(data: Array<Data>) {
    this.latestData.push(...data);
    this.map.on('load', () => {
      this.map.addSource("devices", {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': this.latestData.map(g => g.asFeature())
        }
      });
      this.map.addLayer(Data.getDataStyle("devices"));
    });
  }

  buildGarden() {
    console.log("Oi mate")
    const gardenArea = this.draw.getSelected().features[0].geometry as Polygon;
    const command = CreateGardeningAreaCommand.build(this.gardenName, gardenArea.coordinates[0]);
    this.createGardenService.execute(command).subscribe(
      next => {
        this.isDrawing = false
        this.map.removeControl(this.draw);
        this.gardenName = "";
        this.gardens.push(next);
        this.updateGardensSource();
      },
      error => error);
  }

  onSelect(garden: GardeningArea) {
    this.map.fitBounds(garden.bounds() as LngLatBoundsLike, {padding: 20});
  }

  onDelete(event: MouseEvent, garden: GardeningArea) {
    event.stopPropagation();
    this.deleteGardenService.getData(new DeleteGardeningAreaCommand(garden.id)).subscribe(
      next => {
        this.gardens = this.gardens.filter(elem => elem.id.value !== next.id.value);
        this.updateGardensSource();
      }
    )
  }

  onEdit(event: MouseEvent, garden: GardeningArea) {
    event.stopPropagation();
    //TODO:
    console.log("todo:edit");
  }

  private checkIfSketchIsValid() {
    const gardenArea = this.draw.getAll().features[0].geometry as Polygon;
    if (gardenArea.coordinates[0].length > 2) {
      this.isSketchValid = true;
    }
  }
}

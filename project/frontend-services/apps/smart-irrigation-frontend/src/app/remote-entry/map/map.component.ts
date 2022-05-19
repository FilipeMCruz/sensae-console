import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {Subscription} from 'rxjs';
import {environment} from "../../../environments/environment";
import {
  CreateGardeningAreaCommand, Data, DeleteGardeningAreaCommand,
  GardeningArea,
  LatestDataQueryFilters, UpdateGardeningAreaCommand
} from "@frontend-services/smart-irrigation/model";
import {
  CreateGarden,
  DeleteGarden,
  FetchGardens,
  FetchLatestData,
  UpdateGarden
} from "@frontend-services/smart-irrigation/services";
import * as MapboxDraw from "@mapbox/mapbox-gl-draw";
import {Point, Polygon} from "geojson";
import {GeoJSONSource, LngLatBoundsLike, LngLatLike} from "mapbox-gl";
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
  public isEditing = false;

  public isSketchValid = false;

  public gardenName = "";

  public editing!: GardeningArea;

  style = 'Light';

  styles: string[] = ['Light', 'Satellite'];

  constructor(private fetchGardensService: FetchGardens,
              private createGardenService: CreateGarden,
              private deleteGardenService: DeleteGarden,
              private updateGardenService: UpdateGarden,
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
      next => {
        this.gardens = next;
        this.map.on('load', () => {
          this.drawGardens();
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
      },
      error => error,
      () => this.loadingGardens = false
    );
  }

  private drawGardens() {
    if (this.map.getLayer('gardens-outline')) {
      this.map.removeLayer('gardens-outline');
    }
    if (this.map.getLayer('gardens-labels')) {
      this.map.removeLayer('gardens-labels');
    }
    if (this.map.getLayer('gardens')) {
      this.map.removeLayer('gardens');
    }
    if (this.map.getSource('gardens')) {
      this.map.removeSource('gardens');
    }

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
    this.loadingGardens = false;
  }

  private updateGardensSource() {
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
      next => {
        this.latestData.push(...next);
        this.map.on('load', () => {
          this.drawLatestData();
          const popup = new mapboxgl.Popup({
            maxWidth: 'none',
            closeButton: false,
            closeOnClick: false
          });
          this.map.on('mouseenter', 'devices', (e) => {
            this.map.getCanvas().style.cursor = 'pointer';

            if (e.features && e.features[0] && e.features[0].properties && e.features[0].properties["description"] && e.features[0].geometry) {
              const cords = e.features[0].geometry as Point;
              const coordinates = cords.coordinates.slice();
              const description = e.features[0].properties["description"];

              while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
                coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
              }

              popup.setLngLat(coordinates as LngLatLike).setHTML(description).addTo(this.map);
            }
          });

          this.map.on('mouseleave', 'devices', () => {
            this.map.getCanvas().style.cursor = '';
            popup.remove();
          });
        });
      },
      error => error);
  }

  drawLatestData() {
    if (this.map.getLayer('devices')) {
      this.map.removeLayer('devices');
    }

    if (this.map.getSource('devices')) {
      this.map.removeSource('devices');
    }

    this.map.addSource("devices", {
      'type': 'geojson',
      'data': {
        'type': 'FeatureCollection',
        'features': this.latestData.map(g => g.asFeature())
      }
    });

    this.map.addLayer(Data.getDataStyle("devices"));
  }

  buildGarden() {
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
    this.map.fitBounds(garden.bounds() as LngLatBoundsLike, {padding: 100});
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
    this.draw = new MapboxDraw({
      displayControlsDefault: false,
      defaultMode: 'simple_select'
    });
    this.map.addControl(this.draw);
    this.map.on('draw.create', () => this.checkIfSketchIsValid());
    this.map.on('draw.update', () => this.checkIfSketchIsValid());
    this.editing = garden;
    this.gardens = this.gardens.filter(elem => elem.id.value !== garden.id.value);
    this.updateGardensSource();
    this.isDrawing = true;
    this.isEditing = true;
    this.draw.add(garden.asFeature());
  }

  private checkIfSketchIsValid() {
    const gardenArea = this.draw.getAll().features[0].geometry as Polygon;
    if (gardenArea.coordinates[0].length > 2) {
      this.isSketchValid = true;
    }
  }

  deleteGardenEditSketch() {
    const data = this.draw.getAll();
    if (data.features.length > 0) {
      const id = data.features[0].id;
      if (id) this.draw.delete(id.toLocaleString());
    }
    this.map.removeControl(this.draw);
    this.gardens.push(this.editing);
    this.updateGardensSource();
    this.isDrawing = false
    this.isEditing = false;
  }

  editGarden() {
    const gardenArea = this.draw.getSelected().features[0].geometry as Polygon;
    const command = UpdateGardeningAreaCommand.build(this.editing.id.value, this.editing.name.value, gardenArea.coordinates[0]);
    this.updateGardenService.execute(command).subscribe(
      next => {
        this.isDrawing = false
        this.isEditing = false;
        this.map.removeControl(this.draw);
        this.gardens.push(next);
        this.updateGardensSource();
      },
      error => error);
  }

  changeStyle(event: MouseEvent, value: string) {
    event.stopPropagation();
    this.style = value;
    if (this.style === "Light") {
      this.map.setStyle('mapbox://styles/mapbox/light-v10');
    } else {
      this.map.setStyle('mapbox://styles/mapbox/satellite-v9');
    }
    this.map.on('style.load', () => {
      this.drawGardens();
      this.drawLatestData();
    });
  }

  canDelete() {
    return this.deleteGardenService.canDo();
  }

  canEdit() {
    return this.createGardenService.canDo();
  }

  canCreate() {
    return this.createGardenService.canDo();
  }
}

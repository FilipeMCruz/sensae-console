import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {Subscription} from 'rxjs';
import {environment} from "../../../environments/environment";
import {
  CreateIrrigationZoneCommand, Data, DeleteIrrigationZoneCommand,
  IrrigationZone,
  LatestDataQueryFilters, UpdateIrrigationZoneCommand
} from "@frontend-services/smart-irrigation/model";
import {
  CreateIrrigationZone,
  DeleteIrrigationZone,
  FetchIrrigationZones,
  FetchLatestData,
  UpdateIrrigationZone
} from "@frontend-services/smart-irrigation/services";
import * as MapboxDraw from "@mapbox/mapbox-gl-draw";
import {Point, Polygon} from "geojson";
import {GeoJSONSource, LngLatBoundsLike, LngLatLike} from "mapbox-gl";
import {MatDialog} from "@angular/material/dialog";
import {ZoneDialogComponent} from "../zone-dialog/zone-dialog.component";

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

  public irrigationZones: Array<IrrigationZone> = [];

  public loadingZones = true;

  public isDrawing = false;
  public isEditing = false;

  public isSketchValid = false;

  public gardenName = "";

  public editing!: IrrigationZone;

  style = 'Light';

  styles: string[] = ['Light', 'Satellite'];

  constructor(private fetchIrrigationZoneService: FetchIrrigationZones,
              private createIrrigationZoneService: CreateIrrigationZone,
              private deleteIrrigationZoneService: DeleteIrrigationZone,
              private updateIrrigationZoneService: UpdateIrrigationZone,
              private fetchLatestDataService: FetchLatestData,
              private dialog: MatDialog) {
  }

  ngAfterViewInit(): void {
    this.initializeMap();
    this.fetchGardens();
    this.fetchLatestData();
  }

  private fetchGardens() {
    this.loadingZones = true;
    this.map.on('load', () => {
      this.fetchIrrigationZoneService.getData().subscribe(
        next => {
          this.irrigationZones = next;
          this.drawGardens();
          this.map.on('click', 'zones', (e) => {
            if (e.features && e.features[0] && e.features[0].properties && e.features[0].properties["id"])
              this.openIrrigationZoneDetails(e.features[0].properties["id"]);
          });
          this.map.on('mouseenter', 'zones', () => {
            this.map.getCanvas().style.cursor = 'pointer';
          });
          this.map.on('mouseleave', 'zones', () => {
            this.map.getCanvas().style.cursor = '';
          });
        },
        error => error,
        () => this.loadingZones = false
      );
    });
  }

  private drawGardens() {
    if (this.map.getLayer('zones-outline')) {
      this.map.removeLayer('zones-outline');
    }
    if (this.map.getLayer('zones-labels')) {
      this.map.removeLayer('zones-labels');
    }
    if (this.map.getLayer('zones')) {
      this.map.removeLayer('zones');
    }
    if (this.map.getSource('zones')) {
      this.map.removeSource('zones');
    }

    this.map.addSource("zones", {
      'type': 'geojson',
      'data': {
        'type': 'FeatureCollection',
        'features': this.irrigationZones.map(g => g.asFeature())
      }
    })

    this.map.addLayer(IrrigationZone.getBoundaryStyle("zones"));
    this.map.addLayer(IrrigationZone.getLabelStyle("zones"));
    this.map.addLayer(IrrigationZone.getAreaStyle("zones"));
    this.loadingZones = false;
  }

  private updateGardensSource() {
    const source = this.map.getSource("zones") as GeoJSONSource;
    source.setData({
      'type': 'FeatureCollection',
      'features': this.irrigationZones.map(g => g.asFeature())
    });
  }

  openIrrigationZoneDetails(id: string) {
    this.dialog.open(ZoneDialogComponent, {
      width: '70%',
      height: '80%',
      data: this.irrigationZones.find(g => g.id.value == id),
    });
  }

  ngOnDestroy() {
    if (this.subscription)
      this.subscription.unsubscribe();
  }

  buildMap(): void {
    this.map = new mapboxgl.Map({
      container: 'map',
      style: environment.mapbox.simpleStyle,
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

  startIrrigationZoneSketch() {
    this.draw = new MapboxDraw({
      displayControlsDefault: false,
      defaultMode: 'draw_polygon'
    });
    this.map.addControl(this.draw);
    this.map.on('draw.create', () => this.checkIfSketchIsValid());
    this.map.on('draw.update', () => this.checkIfSketchIsValid());
    this.isDrawing = true;
  }

  deleteIrrigationZoneSketch() {
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
    this.map.on('load', () => {
      this.fetchLatestDataService.getData(filter).subscribe(
        next => {
          this.latestData.push(...next);
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
        },
        error => error);
    });
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

  buildIrrigationZone() {
    const zoneArea = this.draw.getSelected().features[0].geometry as Polygon;
    const command = CreateIrrigationZoneCommand.build(this.gardenName, zoneArea.coordinates[0]);
    this.createIrrigationZoneService.execute(command).subscribe(
      next => {
        this.isDrawing = false
        this.map.removeControl(this.draw);
        this.gardenName = "";
        this.irrigationZones.push(next);
        this.updateGardensSource();
      },
      error => error);
  }

  onSelect(garden: IrrigationZone) {
    this.map.fitBounds(garden.bounds() as LngLatBoundsLike, {padding: 100});
  }

  onDelete(event: MouseEvent, garden: IrrigationZone) {
    event.stopPropagation();
    this.deleteIrrigationZoneService.getData(new DeleteIrrigationZoneCommand(garden.id)).subscribe(
      next => {
        this.irrigationZones = this.irrigationZones.filter(elem => elem.id.value !== next.id.value);
        this.updateGardensSource();
      }
    )
  }

  onEdit(event: MouseEvent, garden: IrrigationZone) {
    event.stopPropagation();
    this.draw = new MapboxDraw({
      displayControlsDefault: false,
      defaultMode: 'simple_select'
    });
    this.map.addControl(this.draw);
    this.map.on('draw.create', () => this.checkIfSketchIsValid());
    this.map.on('draw.update', () => this.checkIfSketchIsValid());
    this.editing = garden;
    this.irrigationZones = this.irrigationZones.filter(elem => elem.id.value !== garden.id.value);
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

  deleteIrrigationZoneEditSketch() {
    const data = this.draw.getAll();
    if (data.features.length > 0) {
      const id = data.features[0].id;
      if (id) this.draw.delete(id.toLocaleString());
    }
    this.map.removeControl(this.draw);
    this.irrigationZones.push(this.editing);
    this.updateGardensSource();
    this.isDrawing = false
    this.isEditing = false;
  }

  editIrrigationZone() {
    const zoneArea = this.draw.getSelected().features[0].geometry as Polygon;
    const command = UpdateIrrigationZoneCommand.build(this.editing.id.value, this.editing.name.value, zoneArea.coordinates[0]);
    this.updateIrrigationZoneService.execute(command).subscribe(
      next => {
        this.isDrawing = false
        this.isEditing = false;
        this.map.removeControl(this.draw);
        this.irrigationZones.push(next);
        this.updateGardensSource();
      },
      error => error);
  }

  changeStyle(event: MouseEvent, value: string) {
    event.stopPropagation();
    this.style = value;
    if (this.style === "Light") {
      this.map.setStyle(environment.mapbox.simpleStyle);
    } else {
      this.map.setStyle(environment.mapbox.satelliteStyle);
    }
    this.map.on('style.load', () => {
      this.drawGardens();
      this.drawLatestData();
    });
  }

  canDelete() {
    return this.deleteIrrigationZoneService.canDo();
  }

  canEdit() {
    return this.createIrrigationZoneService.canDo();
  }

  canCreate() {
    return this.createIrrigationZoneService.canDo();
  }
}

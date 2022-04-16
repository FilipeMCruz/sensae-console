import {Component, OnDestroy, OnInit} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {GeoJSONSource} from 'mapbox-gl';
import {Subscription} from 'rxjs';
import {environment} from '../../../environments/environment';
import {
  QueryGPSDeviceHistory,
  QueryLatestGPSDeviceData,
  QueryLatestGPSSpecificDeviceData,
  SubscribeToAllGPSData,
  SubscribeToGPSDataByContent,
  SubscribeToGPSDataByDevice,
} from '@frontend-services/fleet-management/services';
import {DevicePastDataMapper} from '@frontend-services/fleet-management/mapper';
import {
  Device,
  DeviceData,
  DeviceHistory,
  DeviceHistoryQuery,
  DeviceHistorySegmentType,
  DeviceHistorySource,
  GPSPointData,
} from '@frontend-services/fleet-management/model';
import {AuthService} from "@frontend-services/simple-auth-lib";

@Component({
  selector: 'frontend-services-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit, OnDestroy {

  private map!: mapboxgl.Map;

  private lat = 40.6045295;

  private lng = -7.8474056;

  private points: Array<GPSPointData> = new Array<GPSPointData>();

  history = new DeviceHistorySource();

  private subscription!: Subscription;

  devices: Array<Device> = [];

  currentHistoryTime = 0;

  constructor(private authService: AuthService,
              private locationEmitter: SubscribeToAllGPSData,
              private latestSpecificDeviceData: QueryLatestGPSSpecificDeviceData,
              private locationByDeviceIdEmitter: SubscribeToGPSDataByDevice,
              private locationByContentEmitter: SubscribeToGPSDataByContent,
              private historyQuery: QueryGPSDeviceHistory,
              private latestDeviceData: QueryLatestGPSDeviceData
  ) {
  }

  ngOnInit(): void {
    this.initializeMap();
    this.latestDeviceData.getData().subscribe((next: Array<DeviceData>) => {
      next.forEach((d) => this.drawPoint(d, true));
      this.devices = next.map((d) => d.device);
    });

    this.subscription = this.locationEmitter
      .getData()
      .subscribe((next: DeviceData) => this.drawPoint(next, false));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  buildHistory(filters: DeviceHistoryQuery) {
    this.points.forEach((p) => p.point.remove());
    this.points.splice(0, this.points.length);
    this.subscription.unsubscribe();
    this.historyQuery
      .getData(DevicePastDataMapper.modelToDto(filters))
      .subscribe((next: Array<DeviceHistory>) => {
        this.cleanHistory();
        this.history.setHistories(next);
        this.addHistory();
        this.setPopups();
      });
  }

  cleanSubscriber() {
    this.subscription.unsubscribe();
    this.subscription = this.locationEmitter
      .getData()
      .subscribe((next: DeviceData) => this.drawPoint(next, false));
  }

  subscribeToDevice(devices: Array<Device>) {
    this.cleanHistory();
    this.subscription.unsubscribe();
    this.subscription = this.locationByDeviceIdEmitter
      .getData(devices.map((d) => d.id))
      .subscribe((next: DeviceData) => this.drawPoint(next, false));

    const toRemove = this.points.filter(
      (sensor) => !sensor.value.isAny(devices)
    );
    toRemove.forEach((p) => p.point.remove());
    this.points = this.points.filter((sensor) => sensor.value.isAny(devices));

    const idsToReFetch = devices
      .filter((d) => !this.points.some((old) => old.value.device.id === d.id))
      .map((d) => d.id);
    if (idsToReFetch.length !== 0) {
      this.latestSpecificDeviceData
        .getData(idsToReFetch)
        .subscribe((next: Array<DeviceData>) =>
          next.forEach((d: DeviceData) => this.drawPoint(d, true))
        );
    }
  }

  subscribeToContent(content: string) {
    this.cleanHistory();
    this.subscription.unsubscribe();

    const toRemove = this.points.filter(
      (sensor) => !sensor.value.device.hasContent(content)
    );
    toRemove.forEach((p) => p.point.remove());
    this.points = this.points.filter((sensor) =>
      sensor.value.device.hasContent(content)
    );

    this.subscription = this.locationByContentEmitter
      .getData(content)
      .subscribe((next: DeviceData) => this.drawPoint(next, false));
  }

  addHistory() {
    this.history
      .getPathSources()
      .forEach((history) => this.map.addSource(history.id, history.source));
    this.history.getPathLayers().forEach((layer) => this.map.addLayer(layer));

    this.map.addSource(
      this.history.getStepSourceId(),
      this.history.asGeoJSONForTime(this.history.deviceHistories[0].startTime)
    );
    this.map.addLayer(this.history.getStepLayer());
    this.currentHistoryTime = this.history.startTime;
  }

  cleanHistory() {
    if (!this.history.isEmpty()) {
      this.map.removeLayer(this.history.getStepSourceId());
      this.map.removeSource(this.history.getStepSourceId());
    }
    this.history
      .getPathLayersIds()
      .forEach((layer) => this.map.removeLayer(layer));
    this.history
      .getPathSourcesIds()
      .forEach((source) => this.map.removeSource(source));
    this.history.cleanHistories();
  }

  showDevices() {
    const source = this.map.getSource(
      this.history.getStepSourceId()
    ) as GeoJSONSource;
    source.setData(
      this.history.asGoeJsonFeatureCollection(this.currentHistoryTime)
    );
  }

  formatLabel(): string {
    const start = new Date(this.currentHistoryTime);
    return start.toLocaleDateString() + ' ' + start.toLocaleTimeString();
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

  private setPopups() {
    this.history.deviceHistories.forEach((h) => {
      const popup = new mapboxgl.Popup({maxWidth: 'none'});
      h.getLayersId()
        .filter(
          (l) => !l.endsWith(DeviceHistorySegmentType.INACTIVE.toString())
        )
        .forEach((l) => {
          this.map.on('click', l, (e) => {
            if (e.features && e.features[0].properties != null) {
              popup
                .setLngLat(e.lngLat)
                .setHTML(
                  '<strong>Device Name:</strong> ' +
                  h.deviceName +
                  '<br><strong>Device Id:</strong> ' +
                  h.deviceId +
                  '<br><strong>Distance Travelled:</strong> ' +
                  e.features[0].properties.distance +
                  ' kilometers.'
                )
                .addTo(this.map);
            }
          });
          this.map.on('mouseenter', l, () => {
            this.map.getCanvas().style.cursor = 'pointer';
          });
          this.map.on('mouseleave', l, () => {
            this.map.getCanvas().style.cursor = '';
          });
        });
      const inactiveLayer = h.getLayerId(DeviceHistorySegmentType.INACTIVE);
      this.map.on('click', inactiveLayer, (e) => {
        if (e.features && e.features[0].properties != null) {
          const info = e.features[0].properties;
          const start = new Date(info.start);
          const end = new Date(info.end);
          const startDisplay =
            start.toLocaleDateString() + ' ' + start.toLocaleTimeString();
          const endDisplay =
            end.toLocaleDateString() + ' ' + end.toLocaleTimeString();
          const diffTime = new Date(end.getTime() - start.getTime());
          const diffTimeDisplay = diffTime.toISOString().slice(11, -5);
          let toDisplay;
          if (diffTime.getTime() === 0) {
            toDisplay =
              '<strong>Device Name:</strong> ' +
              h.deviceName +
              '<br><strong>Device Id:</strong> ' +
              h.deviceId +
              '<br><strong>Date:</strong> ' +
              startDisplay;
          } else {
            toDisplay =
              '<strong>Device Name:</strong> ' +
              h.deviceName +
              '<br><strong>Device Id:</strong> ' +
              h.deviceId +
              '<br><strong>Start Date:</strong> ' +
              startDisplay +
              '<br><strong>End Date:</strong> ' +
              endDisplay +
              '<br><strong>Stop Duration:</strong> ' +
              diffTimeDisplay;
          }

          popup.setLngLat(e.lngLat).setHTML(toDisplay).addTo(this.map);
        }
      });
      this.map.on('mouseenter', inactiveLayer, () => {
        this.map.getCanvas().style.cursor = 'pointer';
      });
      this.map.on('mouseleave', inactiveLayer, () => {
        this.map.getCanvas().style.cursor = '';
      });
    });
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

  private drawPoint(sensor: DeviceData, last: boolean): void {
    const found = this.points.find((point) => point.isSameSensor(sensor));
    if (found === undefined) {
      const newPoint = new GPSPointData(sensor, last);
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
        const newPoint = new GPSPointData(sensor, last);
        newPoint.point.addTo(this.map);
        this.points.push(newPoint);
      } else {
        found.updateGPSData(sensor);
      }
    }
  }

  canViewLiveOrPastData() {
    return this.authService.isAllowed(["fleet_management:live_data:read"]) ||
      this.authService.isAllowed(["fleet_management:past_data:read"]);
  }
}

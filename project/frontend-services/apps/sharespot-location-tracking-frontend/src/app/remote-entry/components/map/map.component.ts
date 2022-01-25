import {Component, OnDestroy, OnInit} from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import {DeviceData} from "../../model/DeviceData";
import {GPSPointData} from "../../model/GPSPointData";
import {Subscription} from "rxjs";
import {SubscribeToAllGPSData} from "../../services/SubscribeToAllGPSData";
import {SensorMapper} from "../../mappers/SensorMapper";
import {SubscribeToGPSDataByDevice} from "../../services/SubscribeToGPSDataByDevice";
import {environment} from "../../../../environments/environment";
import {SensorDataDTO} from "../../dtos/SensorDTO";
import {SubscribeToGPSDataByContent} from "../../services/SubscribeToGPSDataByContent";
import {QueryGPSDeviceHistory} from "../../services/QueryGPSDeviceHistory";
import {DeviceHistory} from "../../model/DeviceHistory";
import {QueryLatestGPSDeviceData} from "../../services/QueryLatestGPSDeviceData";
import {Device} from "../../model/Device";
import {DeviceMapper} from "../../mappers/DeviceMapper";
import {DeviceHistoryQuery} from "../../model/DeviceHistoryQuery";
import {DeviceHistoryMapper} from "../../mappers/DeviceHistoryMapper";

@Component({
  selector: 'frontend-services-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit, OnDestroy {

  private map!: mapboxgl.Map;

  private lat = 40.6045295;

  private lng = -7.8474056;

  private points: Array<GPSPointData> = new Array<GPSPointData>();

  private history: Array<DeviceHistory> = [];

  private subscription!: Subscription;

  devices: Array<Device> = [];

  constructor(private locationEmitter: SubscribeToAllGPSData,
              private locationByDeviceIdEmitter: SubscribeToGPSDataByDevice,
              private locationByContentEmitter: SubscribeToGPSDataByContent,
              private historyQuery: QueryGPSDeviceHistory,
              private latestDeviceData: QueryLatestGPSDeviceData) {
  }

  ngOnInit(): void {
    this.initializeMap();
    this.latestDeviceData.getData().subscribe(
      next => {
        if (next.data && next.data.latest) {
          next.data.latest.forEach(d => this.verifyAndDraw(d, "#a9d6e5"));
          this.devices = next.data.latest.map(d => DeviceMapper.dtoToModel(d.device));
        }
      }
    )
    ;
    this.subscription = this.locationEmitter.getData().subscribe(
      next => {
        if (next.data !== undefined && next.data !== null) this.verifyAndDraw(next.data.locations)
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  buildHistory(filters: DeviceHistoryQuery) {
    this.points.forEach(p => p.point.remove());
    this.points.splice(0, this.points.length);
    this.historyQuery.getData(DeviceHistoryMapper.modelToDto(filters)).subscribe(
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

  cleanSubscriber() {
    this.subscription.unsubscribe();
    this.subscription = this.locationEmitter.getData().subscribe(
      next => {
        if (next.data !== undefined && next.data !== null) this.verifyAndDraw(next.data.locations)
      }
    );
  }

  subscribeToDevice(devices: Array<Device>) {
    this.cleanHistory();
    this.subscription.unsubscribe();
    this.points.forEach((sensor, index, array) => {
      if (!sensor.value.isAny(devices)) {
        sensor.point.remove();
        array.splice(index, 1);
      }
    });

    this.subscription = this.locationByDeviceIdEmitter.getData(devices.map(d => d.id)).subscribe(
      next => {
        if (next.data !== undefined && next.data !== null) {
          this.verifyAndDraw(next.data.locations)
        }
      }
    );
  }

  subscribeToContent(content: string) {
    this.cleanHistory();
    this.subscription.unsubscribe();
    this.points.forEach((sensor, index, array) => {
      if (!sensor.value.device.hasContent(content)) {
        sensor.point.remove();
        array.splice(index, 1);
      }
    });

    this.subscription = this.locationByContentEmitter.getData(content).subscribe(
      next => {
        if (next.data !== undefined && next.data !== null) this.verifyAndDraw(next.data.locations)
      }
    );
  }

  addHistory() {
    this.history.forEach(h => {
      this.map.addSource('route-' + h.deviceId, h.asGeoJSON());
      this.map.addLayer(DeviceHistory.buildLayer('route-' + h.deviceId));
    })
  }

  cleanHistory() {
    this.history.forEach(h => {
      this.map.removeLayer('route-' + h.deviceId);
      this.map.removeSource('route-' + h.deviceId);
    });
    this.history.splice(0, this.history.length);
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
    this.history.forEach(h => {
      const popup = new mapboxgl.Popup({maxWidth: 'none'});
      const distance = h.distance;
      this.map.on('click', 'route-' + h.deviceId, (e) => {
        popup.setLngLat(e.lngLat)
          .setHTML("<strong>Device Name:</strong> " + h.deviceName +
            "<br><strong>Device Id:</strong> " + h.deviceId +
            "<br><strong>Distance Travelled:</strong> " + distance + " kilometers.")
          .addTo(this.map);
      });
      this.map.on('mouseenter', 'route-' + h.deviceId, () => {
        this.map.getCanvas().style.cursor = 'pointer';
      });
      this.map.on('mouseleave', 'route-' + h.deviceId, () => {
        this.map.getCanvas().style.cursor = '';
      });
    })
  }

  private verifyAndDraw(data: SensorDataDTO | null | undefined, color?: string) {
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
    // if (sensor.data.gps.longitude < 2 && sensor.data.gps.longitude > -2 && sensor.data.gps.latitude < 2 && sensor.data.gps.latitude > -2) {
    //   return;
    // }

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
}

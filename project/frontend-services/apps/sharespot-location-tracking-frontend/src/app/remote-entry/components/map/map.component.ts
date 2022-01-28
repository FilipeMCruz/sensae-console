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
import {QueryLatestGPSDeviceData} from "../../services/QueryLatestGPSDeviceData";
import {Device} from "../../model/Device";
import {DeviceMapper} from "../../mappers/DeviceMapper";
import {DeviceHistoryQuery} from "../../model/DeviceHistoryQuery";
import {DeviceHistoryMapper} from "../../mappers/DeviceHistoryMapper";
import {DeviceHistory} from "../../model/DeviceHistory";
import {DeviceHistorySegmentType} from "../../model/DeviceHistorySegmentType";

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
          next.data.latest.forEach(d => this.verifyAndDraw(d, true));
          this.devices = next.data.latest.map(d => DeviceMapper.dtoToModel(d.device));
        }
      }
    );
    this.subscription = this.locationEmitter.getData().subscribe(
      next => {
        if (next.data !== undefined && next.data !== null) this.verifyAndDraw(next.data.locations, false)
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  buildHistory(filters: DeviceHistoryQuery) {
    this.points.forEach(p => p.point.remove());
    this.points.splice(0, this.points.length);
    this.subscription.unsubscribe();
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
        if (next.data !== undefined && next.data !== null) this.verifyAndDraw(next.data.locations, false)
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
          this.verifyAndDraw(next.data.locations, false)
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
        if (next.data !== undefined && next.data !== null) this.verifyAndDraw(next.data.locations, false)
      }
    );
  }

  addHistory() {
    this.history.forEach(h => {
      this.map.addSource('route-' + h.deviceId, h.asGeoJSON());
      DeviceHistory.buildLayers('route-' + h.deviceId).forEach(layer => this.map.addLayer(layer));
    })
  }

  cleanHistory() {
    this.history.forEach(h => {
      this.map.removeLayer('route-' + h.deviceId + "-" + DeviceHistorySegmentType.ACTIVE.toString());
      this.map.removeLayer('route-' + h.deviceId + "-" + DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString());
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
      this.map.on('click', 'route-' + h.deviceId + "-" + DeviceHistorySegmentType.ACTIVE.toString(), (e) => {
        popup.setLngLat(e.lngLat)
          .setHTML("<strong>Device Name:</strong> " + h.deviceName +
            "<br><strong>Device Id:</strong> " + h.deviceId +
            "<br><strong>Distance Travelled:</strong> " + distance + " kilometers.")
          .addTo(this.map);
      });
      this.map.on('click', 'route-' + h.deviceId + "-" + DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString(), (e) => {
        popup.setLngLat(e.lngLat)
          .setHTML("<strong>Device Name:</strong> " + h.deviceName +
            "<br><strong>Device Id:</strong> " + h.deviceId +
            "<br><strong>Distance Travelled:</strong> " + distance + " kilometers.")
          .addTo(this.map);
      });
      this.map.on('mouseenter', 'route-' + h.deviceId + "-" + DeviceHistorySegmentType.ACTIVE.toString(), () => {
        this.map.getCanvas().style.cursor = 'pointer';
      });
      this.map.on('mouseleave', 'route-' + h.deviceId + "-" + DeviceHistorySegmentType.ACTIVE.toString(), () => {
        this.map.getCanvas().style.cursor = '';
      });
      this.map.on('mouseenter', 'route-' + h.deviceId + "-" + DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString(), () => {
        this.map.getCanvas().style.cursor = 'pointer';
      });
      this.map.on('mouseleave', 'route-' + h.deviceId + "-" + DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString(), () => {
        this.map.getCanvas().style.cursor = '';
      });
    })
  }

  private verifyAndDraw(data: SensorDataDTO | null | undefined, last: boolean) {
    if (data !== undefined && data !== null) {
      this.drawPoint(SensorMapper.dtoToModel(data), last);
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

  private drawPoint(sensor: DeviceData, last: boolean): void {
    const found = this.points.find(point => point.isSameSensor(sensor));
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
        found.updateGPSData(sensor)
      }
    }
  }
}

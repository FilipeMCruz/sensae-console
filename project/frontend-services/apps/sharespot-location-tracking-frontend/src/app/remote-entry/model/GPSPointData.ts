import {DeviceData} from "./DeviceData";
import * as mapboxgl from "mapbox-gl";
import {MotionType} from "./DeviceStatus";

export class GPSPointData {

  public value: DeviceData;

  private color = '#012A4A';

  public point: mapboxgl.Marker;

  constructor(value: DeviceData, last: boolean) {
    this.value = value;
    this.color = GPSPointData.defineColor(value, last);
    this.point = new mapboxgl.Marker({
      draggable: false,
      color: GPSPointData.defineColor(value, last)
    });
    this.setPopup().setCoordinates();
  }

  private static defineColor(value: DeviceData, last: boolean): string {
    if (last) {
      return value.data.status.motion === MotionType.INACTIVE ? "#f8888a" : "#a9d6e5";
    } else {
      return value.data.status.motion === MotionType.INACTIVE ? "#f34044" : "#012A4A";
    }
  }

  willChangeColor(value: DeviceData): boolean {
    return this.color != GPSPointData.defineColor(value, false);
  }

  updateGPSData(data: DeviceData): void {
    this.value = data;
    this.updatePopup().setCoordinates();
  }

  isSameSensor(data: DeviceData): boolean {
    return this.value.device.id == data.device.id;
  }

  private setPopup() {
    const popup = new mapboxgl.Popup({offset: 25, maxWidth: 'none'}).setHTML(
      this.value.generatePopupText()
    );
    this.point.setPopup(popup);
    return this;
  }

  private updatePopup() {
    this.point.getPopup().setHTML(this.value.generatePopupText());
    return this;
  }

  private setCoordinates() {
    this.point.setLngLat([this.value.data.gps.longitude, this.value.data.gps.latitude]);
    return this;
  }
}

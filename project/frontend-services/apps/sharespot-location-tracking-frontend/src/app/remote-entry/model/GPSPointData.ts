import {DeviceData} from "./DeviceData";
import * as mapboxgl from "mapbox-gl";
import {MotionType} from "./DeviceStatus";

export class GPSPointData {

  public value: DeviceData;

  private color = '#012A4A';

  public point: mapboxgl.Marker;

  constructor(value: DeviceData, color?: string) {
    this.value = value;
    this.color = GPSPointData.defineColor(value, color);
    this.point = new mapboxgl.Marker({
      draggable: false,
      color: GPSPointData.defineColor(value, color)
    });
    this.setPopup().setCoordinates();
  }

  private static defineColor(value: DeviceData, color?: string): string {
    if (color) return color;
    if (value.data.status.motion === MotionType.INACTIVE) return '#fb4934';
    return '#012A4A';
  }

  willChangeColor(value: DeviceData): boolean {
    return this.color != GPSPointData.defineColor(value);
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

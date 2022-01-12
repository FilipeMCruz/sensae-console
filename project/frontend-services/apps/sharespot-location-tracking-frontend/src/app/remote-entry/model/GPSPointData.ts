import {GPSSensorData} from "./GPSSensorData";
import * as mapboxgl from "mapbox-gl";

export class GPSPointData {

  public value: GPSSensorData;

  public point: mapboxgl.Marker;

  constructor(value: GPSSensorData) {
    this.value = value;
    this.point = new mapboxgl.Marker({
      draggable: false,
    });
    this.setPopup().setCoordinates();
  }

  updateGPSData(data: GPSSensorData): void {
    this.value = data;
    this.updatePopup().setCoordinates();
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
    this.point.setLngLat([this.value.coordinates.longitude, this.value.coordinates.latitude]);
    return this;
  }

  isSameSensor(data: GPSSensorData): boolean {
    return this.value.device.id == data.device.id;
  }
}

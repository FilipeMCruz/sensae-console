import {GPSSensorData} from "./GPSSensorData";
import * as mapboxgl from "mapbox-gl";

export class GPSPointData {

  public value: GPSSensorData;

  public point: mapboxgl.Marker;

  constructor(value: GPSSensorData) {
    this.value = value;
    const el = document.createElement('div');
    el.className = "marker-basic"
    const popup = new mapboxgl.Popup({offset: 25}).setHTML(
      value.generatePopupText()
    );

    this.point = new mapboxgl.Marker(el, {
      draggable: false,
    }).setLngLat([this.value.coordinates.longitude, this.value.coordinates.latitude])
      .setPopup(popup)
  }

  updateGPSData(data: GPSSensorData): void {
    this.value = data;
    this.point.setLngLat([data.coordinates.longitude, data.coordinates.latitude])
  }

  isSameSensor(data: GPSSensorData): boolean {
    return this.value.deviceId == data.deviceId;
  }
}

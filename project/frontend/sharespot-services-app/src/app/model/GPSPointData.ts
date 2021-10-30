import {GPSSensorData} from "./GPSSensorData";
import * as mapboxgl from "mapbox-gl";

export class GPSPointData {

  public value: GPSSensorData;

  public point: mapboxgl.Marker;

  constructor(value: GPSSensorData) {
    this.value = value;
    this.point = new mapboxgl.Marker({
      draggable: false
    }).setLngLat([this.value.coordinates.longitude, this.value.coordinates.latitude])
  }

  updateGPSData(data: GPSSensorData): void {
    this.value = data;
    this.point.setLngLat([data.coordinates.longitude, data.coordinates.latitude])
  }

  isSameSensor(data: GPSSensorData): boolean {
    return this.value.deviceId == data.deviceId;
  }
}

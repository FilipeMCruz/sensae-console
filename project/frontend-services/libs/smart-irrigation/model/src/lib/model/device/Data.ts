import {Device} from "./Device";
import {SensorDataDetails} from "../details/SensorDataDetails";
import {ParkSensorDataDetails} from "../details/ParkSensorDataDetails";
import {StoveSensorDataDetails} from "../details/StoveSensorDataDetails";
import {ValveDataDetails} from "../details/ValveDataDetails";
import {Feature} from "geojson";
import * as mapboxgl from "mapbox-gl";

export class Data {
  constructor(public id: string, public device: Device, public reportedAt: Date, public data: SensorDataDetails) {
  }

  static getDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'devices',
      type: 'circle',
      source,
      paint: {
        'circle-color': '#adb5bd',
        'circle-radius': 4,
        'circle-stroke-width': 1,
        'circle-stroke-color': '#582f0e'
      }
    }
  }

  public update(update: Data) {
    this.id = update.id;
    this.device = update.device;
    this.reportedAt = update.reportedAt;
    this.data = update.data;
  }

  getColor() {
    if (this.data instanceof ParkSensorDataDetails) {
      return {'color': '#656d4a'};
    } else if (this.data instanceof StoveSensorDataDetails) {
      return {'color': '#a68a64'}
    } else if (this.data instanceof ValveDataDetails) {
      return {'color': '#adb5bd'}
    } else {
      return {'color': '#adb5bd'}
    }
  }

  getFirstDataIcon() {
    if (this.data instanceof ParkSensorDataDetails) {
      return "grass";
    } else if (this.data instanceof StoveSensorDataDetails) {
      return "thermostat";
    } else if (this.data instanceof ValveDataDetails) {
      return "warning";
    } else {
      return "";
    }
  }

  getFirstDataDetails(): string {
    if (this.data instanceof ParkSensorDataDetails) {
      return this.data.soilMoisture.percentage + "%";
    } else if (this.data instanceof StoveSensorDataDetails) {
      return this.data.temperature.celsius + "ºC";
    } else if (this.data instanceof ValveDataDetails) {
      return this.data.valve.status.toString();
    } else {
      return "";
    }
  }

  getSecondDataDetails(): string {
    if (this.data instanceof ParkSensorDataDetails) {
      return this.data.illuminance.lux + "";
    } else if (this.data instanceof StoveSensorDataDetails) {
      return this.data.humidity.gramsPerCubicMeter + "g/m3";
    } else {
      return "";
    }
  }

  getSecondDataIcon(): string {
    if (this.data instanceof ParkSensorDataDetails) {
      return 'wb_sunny';
    } else if (this.data instanceof StoveSensorDataDetails) {
      return 'opacity';
    } else {
      return "";
    }
  }

  asFeature(): Feature {
    return {
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: this.data.gps.toCoordinates()
      },
      properties: {
        description: this.device.name.value,
        id: this.device.id.value
      }
    };
  }
}

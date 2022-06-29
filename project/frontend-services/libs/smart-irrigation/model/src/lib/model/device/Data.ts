import {Device} from "./Device";
import {SensorDataDetails} from "../details/SensorDataDetails";
import {ParkSensorDataDetails} from "../details/ParkSensorDataDetails";
import {StoveSensorDataDetails} from "../details/StoveSensorDataDetails";
import {ValveDataDetails} from "../details/ValveDataDetails";
import {Feature} from "geojson";
import * as mapboxgl from "mapbox-gl";
import {DateFormat} from "@frontend-services/core";

export class Data {
  constructor(public id: string, public device: Device, public reportedAt: Date, public data: SensorDataDetails) {
  }

  static getDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'devices',
      type: 'circle',
      source,
      paint: {
        'circle-color': ['get', 'color'],
        'circle-radius': {
          "stops": [
            [0, 0],
            [12, 0],
            [14, 4]
          ]
        },
        'circle-stroke-width': 1,
        'circle-stroke-color': '#582f0e'
      }
    }
  }

  static getIlluminanceDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'devices-illuminance-heat',
      type: 'heatmap',
      source,
      filter: Data.getFilterByProperty('illuminance'),
      paint: {
        'heatmap-weight': {
          property: 'illuminance',
          type: 'identity'
        },
        'heatmap-radius': 50,
        'heatmap-opacity': 0.4,
        'heatmap-color': [
          'interpolate',
          ['linear'],
          ['heatmap-density'],
          0,
          'rgba(236,222,239,0)',
          0.2,
          '#fffae5',
          0.4,
          '#ffee99',
          0.6,
          '#ffe566',
          0.8,
          '#ffdd32',
          0.95,
          '#ffd400'
        ]
      }
    }
  }

  static getSoilMoistureDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'devices-moisture-heat',
      type: 'heatmap',
      source,
      filter: Data.getFilterByProperty('soilMoisture'),
      paint: {
        'heatmap-weight': {
          property: 'soilMoisture',
          type: 'identity'
        },
        'heatmap-radius': 50,
        'heatmap-opacity': 0.4,
        'heatmap-color': [
          'interpolate',
          ['linear'],
          ['heatmap-density'],
          0,
          'rgba(236,222,239,0)',
          0.2,
          '#eeef20',
          0.4,
          '#d4d700',
          0.6,
          '#aacc00',
          0.8,
          '#55a630',
          0.95,
          '#007f5f'
        ]
      }
    }
  }

  private static getFilterByProperty(property: string) {
    return [
      'match',
      ['get', property],
      0,
      false,
      true
    ];
  }

  static getHumidityDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'devices-humidity-heat',
      type: 'heatmap',
      source,
      filter: Data.getFilterByProperty('humidity'),
      paint: {
        'heatmap-weight': {
          property: 'humidity',
          type: 'identity'
        },
        'heatmap-radius': 50,
        'heatmap-opacity': 0.4,
        'heatmap-color': [
          'interpolate',
          ['linear'],
          ['heatmap-density'],
          0,
          'rgba(236,222,239,0)',
          0.2,
          '#caf0f8',
          0.4,
          '#90e0ef',
          0.6,
          '#00b4d8',
          0.8,
          '#0096c7',
          0.95,
          '#03045e'
        ]
      }
    }
  }

  static getTemperatureDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'devices-temperature-heat',
      type: 'heatmap',
      source,
      filter: Data.getFilterByProperty('temperature'),
      paint: {
        'heatmap-weight': {
          property: 'temperature',
          type: 'identity'
        },
        'heatmap-radius': 50,
        'heatmap-opacity': 0.4,
        'heatmap-color': [
          'interpolate',
          ['linear'],
          ['heatmap-density'],
          0,
          'rgba(236,222,239,0)',
          0.2,
          '#005f73',
          0.4,
          '#94d2bd',
          0.6,
          '#e9d8a6',
          0.8,
          '#ca6702',
          0.95,
          '#bb3e03'
        ]
      }
    }
  }

  static getHoverDataStyle(source: string): mapboxgl.AnyLayer {
    return {
      id: 'hoverDevices',
      type: 'circle',
      source,
      paint: {
        'circle-color': ['get', 'color'],
        'circle-radius': 10,
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

  public timeAgo(): string {
    return DateFormat.timeAgo(this.reportedAt);
  }

  getColor() {
    return {'color': this.data.color}
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
      return this.data.soilMoisture.relativePercentage.toFixed(1) + "%";
    } else if (this.data instanceof StoveSensorDataDetails) {
      return this.data.temperature.celsius.toFixed(1) + "ºC";
    } else if (this.data instanceof ValveDataDetails) {
      return this.data.valve.status.toString();
    } else {
      return "";
    }
  }

  getSecondDataDetails(): string {
    if (this.data instanceof ParkSensorDataDetails) {
      return this.data.illuminance.lux.toFixed(1) + "";
    } else if (this.data instanceof StoveSensorDataDetails) {
      return this.data.humidity.relativePercentage.toFixed(1) + "%";
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
    const illuminance = this.data instanceof ParkSensorDataDetails ? this.data.illuminance.lux / 50 : 0;
    const soilMoisture = this.data instanceof ParkSensorDataDetails ? this.data.soilMoisture.relativePercentage * 2 : 0;
    const temperature = this.data instanceof StoveSensorDataDetails ? this.data.temperature.celsius / 60 * 2000 : 0;
    const humidity = this.data instanceof StoveSensorDataDetails ? this.data.humidity.relativePercentage / 30 * 2000 : 0;

    return {
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: this.data.gps.toCoordinates()
      },
      properties: {
        description: `
<strong>Device Name:</strong> ${this.device.name.value}</br>
<strong>Device Id:</strong> ${this.device.id.value}</br>
<strong>Reported:</strong> ${this.timeAgo()} ago</br>
${this.data.getDataDetailsInHTML()}
`,
        id: this.device.id.value,
        color: this.data.color,
        illuminance,
        soilMoisture,
        temperature,
        humidity
      }
    };
  }
}

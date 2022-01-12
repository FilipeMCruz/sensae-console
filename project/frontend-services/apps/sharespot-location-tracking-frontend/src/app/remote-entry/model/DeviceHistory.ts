import {SensorCoordinates} from "./SensorCoordinates";
import {GeoJSONSourceRaw, LineLayer} from "mapbox-gl";

export class DeviceHistory {
  constructor(public deviceName: string, public deviceId: string, public startTime: number, public endTime: number, public gpsData: Array<SensorCoordinates>) {
  }

  static buildLayer(id: string): LineLayer {
    return {
      id: id,
      type: 'line',
      source: 'route',
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-color': 'red',
        'line-width': 6,
        'line-gradient': [
          'interpolate',
          ['linear'],
          ['line-progress'],
          0, '#a9d6e5',
          0.1, '#89c2d9',
          0.2, '#61a5c2',
          0.3, '#468faf',
          0.4, '#2c7da0',
          0.5, '#2a6f97',
          0.6, '#014f86',
          0.7, '#01497c',
          0.8, '#013a63',
          1, '#012A4A'
        ]
      }
    }
  }

  public asGeoJSON(): GeoJSONSourceRaw {
    const coordinates = this.gpsData.map(d => [d.longitude, d.latitude]);
    return {
      type: 'geojson',
      lineMetrics: true,
      data: {
        type: 'Feature',
        properties: {},
        geometry: {
          type: 'LineString',
          coordinates
        }
      }
    }
  }
}


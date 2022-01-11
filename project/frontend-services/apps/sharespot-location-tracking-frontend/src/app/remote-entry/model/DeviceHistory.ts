import {SensorCoordinates} from "./SensorCoordinates";
import {GeoJSONSourceRaw, LineLayer} from "mapbox-gl";

export class DeviceHistory {
  constructor(public deviceName: string, public deviceId: string, public startTime: number, public endTime: number, public gpsData: Array<SensorCoordinates>) {
  }

  static buildLayer(id: string): LineLayer {
    return {
      'id': id,
      'type': 'line',
      'source': 'route',
      'layout': {
        'line-join': 'round',
        'line-cap': 'round'
      },
      'paint': {
        'line-color': '#888',
        'line-width': 8
      }
    }
  }

  public asGeoJSON(): GeoJSONSourceRaw {
    const coordinates = this.gpsData.map(d => [d.longitude, d.latitude]);
    return {
      'type': 'geojson',
      'data': {
        'type': 'Feature',
        'properties': {},
        'geometry': {
          'type': 'LineString',
          'coordinates': coordinates
        }
      }
    }
  }
}


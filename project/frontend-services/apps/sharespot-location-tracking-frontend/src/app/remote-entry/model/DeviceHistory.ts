import {DeviceHistorySegment} from "./DeviceHistorySegment";
import {GeoJSONSourceRaw, LineLayer} from "mapbox-gl";
import {Feature} from "geojson";
import {DeviceHistorySegmentType} from "./DeviceHistorySegmentType";

export class DeviceHistory {
  constructor(public deviceName: string,
              public deviceId: string,
              public startTime: number,
              public endTime: number,
              public distance: number,
              public segments: Array<DeviceHistorySegment>) {
  }

  static buildLayers(id: string): Array<LineLayer> {
    const layers = new Array(4);
    layers.push({
      id: id + "-" + DeviceHistorySegmentType.ACTIVE.toString(),
      type: 'line',
      source: id,
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-width': 4,
        'line-color': '#01497c',
        // 'line-gradient': [
        //   'interpolate',
        //   ['linear'],
        //   ['line-progress'],
        //   0, '#a9d6e5',
        //   0.1, '#89c2d9',
        //   0.2, '#61a5c2',
        //   0.3, '#468faf',
        //   0.4, '#2c7da0',
        //   0.5, '#2a6f97',
        //   0.6, '#014f86',
        //   0.7, '#01497c',
        //   0.8, '#013a63',
        //   1, '#012A4A'
        // ]
      },
      'filter': ['==', ['get', 'status'], DeviceHistorySegmentType.ACTIVE.toString()]
    });
    layers.push({
      id: id + "-" + DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString(),
      type: 'line',
      source: id,
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-dasharray': [2, 4],
        'line-color': '#a9d6e5',
        'line-width': 2,
      },
      'filter': ['==', ['get', 'status'], DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString()]
    });

    return layers;
  }

  asGeoJSON(): GeoJSONSourceRaw {
    return {
      type: 'geojson',
      // lineMetrics: true,
      data: {
        type: 'FeatureCollection',
        features: this.buildFeatures()
      }
    }
  }

  private buildFeatures(): Array<Feature> {
    console.log("Nº seg:", this.segments.length)
    console.log("Nº active seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.ACTIVE).length)
    console.log("Nº unknown active seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.UNKNOWN_ACTIVE).length)
    console.log("Nº inactive seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.INACTIVE).length)
    console.log("Nº unknown inactive seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.UNKNOWN_INACTIVE).length)
    return this.segments.map(seg => {
      const coordinates = seg.steps.map(d => [d.gps.longitude, d.gps.latitude]);
      return {
        type: 'Feature',
        properties: {
          status: seg.type.toString()
        },
        geometry: {
          type: 'LineString',
          coordinates
        }
      }
    })

  }
}

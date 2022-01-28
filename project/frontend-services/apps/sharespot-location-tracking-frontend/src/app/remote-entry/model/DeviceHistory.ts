import {DeviceHistorySegment} from "./DeviceHistorySegment";
import {GeoJSONSourceRaw, LineLayer} from "mapbox-gl";
import {Feature} from "geojson";
import {DeviceHistorySegmentType} from "./DeviceHistorySegmentType";
import {HistoryColorSet} from "./HistoryColorSet";

export class DeviceHistory {
  constructor(public deviceName: string,
              public deviceId: string,
              public startTime: number,
              public endTime: number,
              public distance: number,
              public segments: Array<DeviceHistorySegment>) {
  }

  getSourceId(): string {
    return 'route-' + this.deviceId;
  }

  getLayersId(): Array<string> {
    return [
      this.getLayerId(DeviceHistorySegmentType.ACTIVE),
      this.getLayerId(DeviceHistorySegmentType.UNKNOWN_ACTIVE),
      this.getLayerId(DeviceHistorySegmentType.INACTIVE),
      this.getLayerId(DeviceHistorySegmentType.UNKNOWN_INACTIVE)
    ];
  }

  getLayerId(type: DeviceHistorySegmentType) {
    return this.getSourceId() + "-" + type.toString();
  }

  buildLayers(historyColor: HistoryColorSet): Array<LineLayer> {
    const id = this.getSourceId();
    const layers = new Array(4);
    layers.push({
      id: this.getLayerId(DeviceHistorySegmentType.ACTIVE),
      type: 'line',
      source: id,
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-width': 4,
        'line-color': historyColor.activeColor,
      },
      'filter': ['==', ['get', 'status'], DeviceHistorySegmentType.ACTIVE.toString()]
    });
    layers.push({
      id: this.getLayerId(DeviceHistorySegmentType.UNKNOWN_ACTIVE),
      type: 'line',
      source: id,
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-dasharray': [2, 4],
        'line-color': historyColor.unknownActiveColor,
        'line-width': 2,
      },
      'filter': ['==', ['get', 'status'], DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString()]
    });
    layers.push({
      id: this.getLayerId(DeviceHistorySegmentType.UNKNOWN_INACTIVE),
      type: 'line',
      source: id,
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-dasharray': [2, 4],
        'line-color': '#00ff00',
        'line-width': 2,
      },
      'filter': ['==', ['get', 'status'], DeviceHistorySegmentType.UNKNOWN_INACTIVE.toString()]
    });
    layers.push({
      id: this.getLayerId(DeviceHistorySegmentType.INACTIVE),
      type: 'line',
      source: id,
      layout: {
        'line-join': 'round',
        'line-cap': 'round'
      },
      paint: {
        'line-dasharray': [2, 4],
        'line-color': '#ff0000',
        'line-width': 2,
      },
      'filter': ['==', ['get', 'status'], DeviceHistorySegmentType.INACTIVE.toString()]
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
    return this.segments.filter(s => s.steps.length != 1).map(seg => {
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

import {DeviceHistorySegment, DeviceHistorySegmentType} from "./DeviceHistorySegment";
import {GeoJSONSourceRaw, LineLayer} from "mapbox-gl";
import {Feature} from "geojson";
import {HistoryColorSet} from "./HistoryColorSet";
import {DeviceHistoryStep} from "./DeviceHistoryStep";

export class DeviceHistory {

  public steps: Array<DeviceHistoryStep>;

  constructor(public deviceName: string,
              public deviceId: string,
              public startTime: number,
              public endTime: number,
              public distance: number,
              public segments: Array<DeviceHistorySegment>,
              public colors: HistoryColorSet) {
    this.steps = segments.map(s => s.steps).flat();
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

  buildLayers(): Array<LineLayer> {
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
        'line-color': ['get', 'color'],
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
        'line-color': ['get', 'color'],
        'line-width': 2,
      },
      filter: ['==', ['get', 'status'], DeviceHistorySegmentType.UNKNOWN_ACTIVE.toString()]
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
        'line-color': ['get', 'color'],
        'line-width': 2,
      },
      filter: ['==', ['get', 'status'], DeviceHistorySegmentType.UNKNOWN_INACTIVE.toString()]
    });
    layers.push({
      id: this.getLayerId(DeviceHistorySegmentType.INACTIVE),
      type: 'circle',
      source: id,
      paint: {
        'circle-radius': 8,
        'circle-color': ['get', 'color'],
      },
      filter: ['==', ['get', 'status'], DeviceHistorySegmentType.INACTIVE.toString()]
    });

    return layers;
  }

  asGeoJSON(): GeoJSONSourceRaw {
    return {
      type: 'geojson',
      data: {
        type: 'FeatureCollection',
        features: this.buildFeatures()
      }
    }
  }

  getClosestStepTo(time: number): DeviceHistoryStep | null {
    return this.binarySearch(time);
  }

  areStepsSorted(): boolean {
    return this.steps.every((v, i, a) => !i || a[i - 1].reportedAt <= v.reportedAt);
  }

  private buildFeatures(): Array<Feature> {
    // console.log("Nº seg:", this.segments.length)
    // console.log("Nº active seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.ACTIVE).length)
    // console.log("Nº unknown active seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.UNKNOWN_ACTIVE).length)
    // console.log("Nº inactive seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.INACTIVE).length)
    // console.log("Nº unknown inactive seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.UNKNOWN_INACTIVE).length)
    // console.log("Inactive seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.INACTIVE))
    // console.log("Unknown Inactive seg:", this.segments.filter(s => s.type === DeviceHistorySegmentType.UNKNOWN_INACTIVE))

    return this.segments.map(seg => {
      const coordinates = seg.steps.map(d => d.gps.toCoordinates());
      if (seg.type === DeviceHistorySegmentType.INACTIVE) {
        return {
          type: 'Feature',
          properties: {
            status: seg.type.toString(),
            start: seg.steps[0].reportedAt,
            end: seg.steps[1].reportedAt,
            color: this.colors.pickColor(seg.type),
          },
          geometry: {
            type: 'Point',
            coordinates: coordinates[0],
          }
        }
      } else {
        return {
          type: 'Feature',
          properties: {
            color: this.colors.pickColor(seg.type),
            status: seg.type.toString(),
            distance: this.distance
          },
          geometry: {
            type: 'LineString',
            coordinates
          }
        }
      }
    })
  }

  private binarySearch(target: number): DeviceHistoryStep | null {
    const n = this.steps.length
    if (target <= this.steps[0].reportedAt) {
      return null;
    }
    if (target >= this.steps[n - 1].reportedAt) {
      return this.steps[n - 1];
    }

    let i = 0, j = n, mid = 0;
    while (i < j) {
      mid = Math.floor((i + j) / 2)

      if (this.steps[mid].reportedAt == target) {
        return this.steps[mid];
      }

      /* If target is less than array element,
          then search in left */
      if (target < this.steps[mid].reportedAt) {
        // If target is greater than previous
        // to mid, return closest of two
        if (mid > 0 && target > this.steps[mid - 1].reportedAt) {
          return this.steps[mid - 1];
        }
        /* Repeat for left half */
        j = mid;
      } else {
        // If target is greater than mid
        if (mid < n - 1 && target < this.steps[mid + 1].reportedAt) {
          return this.steps[mid];
        }
        // update i
        i = mid + 1;
      }
    }
    return this.steps[mid];
  }
}

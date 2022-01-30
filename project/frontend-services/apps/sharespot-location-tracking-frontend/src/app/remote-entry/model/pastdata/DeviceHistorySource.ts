import {DeviceHistory} from "./DeviceHistory";
import {CircleLayer, GeoJSONSourceRaw, LineLayer} from "mapbox-gl";
import {DeviceHistorySegmentType} from "./DeviceHistorySegment";
import {Feature, FeatureCollection} from "geojson";
import {isNonNull} from "../../services/ObservableFunctions";

export interface PathSource {
  id: string,
  source: GeoJSONSourceRaw,
}

export class DeviceHistorySource {
  public deviceHistories = new Array<DeviceHistory>();

  getStepSourceId(): string {
    return "steps";
  }

  isEmpty() {
    return this.deviceHistories.length == 0;
  }

  cleanHistories() {
    this.deviceHistories = new Array<DeviceHistory>();
  }

  setHistories(histories: Array<DeviceHistory>) {
    this.deviceHistories.push(...histories);
  }

  asGeoJSONForTime(time: number): GeoJSONSourceRaw {
    return {
      type: 'geojson',
      data: this.asGoeJsonFeatureCollection(time)
    }
  }

  asGoeJsonFeatureCollection(time: number): FeatureCollection {
    return {
      type: 'FeatureCollection',
      features: this.getStepSourceForTime(time)
    }
  }

  private getStepSourceForTime(time: number): Array<Feature> {
    return this.deviceHistories.map(d => d.getClosestStepTo(time))
      .filter(isNonNull)
      .map(d => d.toFeature());
  }

  getPathSources(): Array<PathSource> {
    return this.deviceHistories.map(h => {
        return {id: h.getSourceId(), source: h.asGeoJSON()}
      }
    );
  }

  getPathSourcesIds(): Array<string> {
    return this.deviceHistories.map(d => d.getSourceId());
  }

  getPathLayers(): Array<LineLayer> {
    return this.deviceHistories.map(h => h.buildLayers()).flat();
  }

  getPathLayersIds(): Array<string> {
    return this.deviceHistories.map(d => d.getLayersId()).flat();
  }

  getPathLayerIds(type: DeviceHistorySegmentType): Array<string> {
    return this.deviceHistories.map(d => d.getLayerId(type)).flat();
  }

  getStepLayer(): CircleLayer {
    return {
      id: this.getStepSourceId(),
      type: 'circle',
      source: this.getStepSourceId(),
      paint: {
        'circle-radius': 10,
        'circle-color': "#000000",
      }
    }
  }
}

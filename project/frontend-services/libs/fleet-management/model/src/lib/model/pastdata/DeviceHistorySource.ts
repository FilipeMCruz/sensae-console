import {DeviceHistory} from './DeviceHistory';
import {GeoJSONSourceRaw, LineLayer, SymbolLayer} from 'mapbox-gl';
import {DeviceHistorySegmentType} from './DeviceHistorySegment';
import {Feature, FeatureCollection} from 'geojson';
import {isNonNull} from "@frontend-services/core";

export interface PathSource {
  id: string;
  source: GeoJSONSourceRaw;
}

export class DeviceHistorySource {
  public deviceHistories = new Array<DeviceHistory>();
  public inUse = false;
  public startTime = 0;
  public endTime = 1;

  getStepSourceId(): string {
    return 'steps';
  }

  isEmpty() {
    return this.deviceHistories.length == 0;
  }

  cleanHistories() {
    this.deviceHistories = new Array<DeviceHistory>();
    this.inUse = false;
  }

  setHistories(histories: Array<DeviceHistory>) {
    if (histories.length === 0) {
      return;
    }

    this.startTime = histories[0].startTime;
    this.endTime = histories[0].endTime;
    this.deviceHistories.push(...histories);
    this.inUse = true;
  }

  asGeoJSONForTime(time: number): GeoJSONSourceRaw {
    return {
      type: 'geojson',
      data: this.asGoeJsonFeatureCollection(time),
    };
  }

  asGoeJsonFeatureCollection(time: number): FeatureCollection {
    return {
      type: 'FeatureCollection',
      features: this.getStepSourceForTime(time),
    };
  }

  private getStepSourceForTime(time: number): Array<Feature> {
    return this.deviceHistories
      .map((d) => d.getClosestStepTo(time))
      .filter(isNonNull)
      .map((d) => d.toFeature());
  }

  getPathSources(): Array<PathSource> {
    return this.deviceHistories.map((h) => {
      return {id: h.getSourceId(), source: h.asGeoJSON()};
    });
  }

  getPathSourcesIds(): Array<string> {
    return this.deviceHistories.map((d) => d.getSourceId());
  }

  getPathLayers(): Array<LineLayer> {
    return this.deviceHistories.map((h) => h.buildLayers()).flat();
  }

  getPathLayersIds(): Array<string> {
    return this.deviceHistories.map((d) => d.getLayersId()).flat();
  }

  getPathLayerIds(type: DeviceHistorySegmentType): Array<string> {
    return this.deviceHistories.map((d) => d.getLayerId(type)).flat();
  }

  getStepLayer(style: string): SymbolLayer {
    return {
      id: this.getStepSourceId(),
      type: 'symbol',
      source: this.getStepSourceId(),
      layout: {
        'icon-image': style === "Light" ? 'car-15' : 'car',
        'icon-size': 1.5,
      },
    };
  }
}

import {AreaBoundary} from "./AreaBoundary";
import {IrrigationZoneName} from "./IrrigationZoneName";
import {IrrigationZoneId} from "./IrrigationZoneId";
import center from "@turf/center";
import {Feature} from "geojson";
import * as mapboxgl from "mapbox-gl";
import bbox from "@turf/bbox";

export class IrrigationZone {
  constructor(public id: IrrigationZoneId, public name: IrrigationZoneName, public area: Array<AreaBoundary>) {
  }

  static getLabelStyle(source: string): mapboxgl.AnyLayer {
    return {
      'id': 'zones-labels',
      'type': 'symbol',
      source,
      'layout': {
        'text-field': ['get', 'description'],
        'text-variable-anchor': ['top', 'bottom', 'left', 'right'],
        'text-radial-offset': 0.5,
        'text-justify': 'auto',
        'icon-image': "garden-15"
      }
    }
  }

  static getAreaStyle(source: string): mapboxgl.AnyLayer {
    return {
      'id': 'zones',
      'type': 'fill',
      source,
      'layout': {},
      'paint': {
        'fill-color': '#a4ac86',
        'fill-opacity': 0.5
      }
    }
  }

  static getBoundaryStyle(source: string): mapboxgl.AnyLayer {
    return {
      'id': 'zones-outline',
      'type': 'line',
      source,
      'layout': {},
      'paint': {
        'line-color': '#582f0e',
        'line-width': 2
      }
    }
  }

  bounds(): number[] {
    const feature = {
      type: 'MultiPoint',
      coordinates: this.getArea(),
    };
    return bbox(feature)
  }

  center(): number[] {
    const feature = {
      type: 'MultiPoint',
      coordinates: this.getArea(),
    };
    return center(feature).geometry.coordinates;
  }

  asFeature(): Feature {
    return {
      type: 'Feature',
      geometry: {
        type: 'Polygon',
        coordinates: [
          this.getArea()
        ]
      },
      properties: {
        description: this.name.value,
        id: this.id.value
      }
    };
  }

  getArea() {
    const cords = [];
    for (let i = 0; i < this.area.length; i++) {
      const boundary = this.area.find(b => b.position == i);
      if (boundary) cords.push(boundary.point.toCoordinates())
    }
    //to close the area
    const boundary = this.area.find(b => b.position == 0);
    if (boundary) cords.push(boundary.point.toCoordinates())
    return cords;
  }
}
